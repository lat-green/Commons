package com.greentree.commons.event.container;

import com.greentree.commons.event.ListenerCloser;
import com.greentree.commons.util.iterator.IteratorUtil;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class MultiContainer<L> implements ListenerContainer<L>, Externalizable {

    private static final long serialVersionUID = 1L;

    private final List<ListenerInfo<L>> listeners = new ArrayList<>();

    private final Collection<ListenerInfo<L>> _add = new ArrayList<>();
    private final Collection<L> _remove = new ArrayList<>();

    @Override
    public ListenerCloser add(L listener) {
        var info = new ListenerInfo<L>(listener, new ListenerNotCloseException());
        synchronized (_add) {
            _add.add(info);
        }
        return new MultiContainerListenerCloser<>(_remove, listener);
    }

    @Override
    public int size() {
        synchronized (listeners) {
            updateListeners();
            return listeners.size();
        }
    }

    @Override
    public void clear() {
        synchronized (listeners) {
            updateListeners();
            listeners.clear();
        }
    }

    private void updateListeners() {
        synchronized (listeners) {
            synchronized (_add) {
                listeners.addAll(_add);
                _add.clear();
            }
            synchronized (_remove) {
                listeners.removeIf(x -> _remove.contains(x.listener.get()));
                _remove.clear();
            }
            for (ListenerInfo<L> listener : listeners) {
                if (!listener.isValid())
                    throw listener.exception;

            }
        }
    }

    public boolean isEmpty() {
        synchronized (listeners) {
            updateListeners();
            return listeners.isEmpty();
        }
    }

    @Override
    public Iterator<L> iterator() {
        synchronized (listeners) {
            updateListeners();
            return IteratorUtil.map(listeners, x -> x.listener.get()).iterator();
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    private record ListenerInfo<L>(
            WeakReference<L> listener,
            RuntimeException exception
    ) {

        ListenerInfo(L listener, RuntimeException exception) {
            this(new WeakReference<>(listener), exception);
        }

        public boolean isValid() {
            return listener.get() != null;
        }

    }

    private record MultiContainerListenerCloser<L>(
            Collection<L> _remove,
            L listener
    ) implements ListenerCloser {

        @Override
        public void close() {
            synchronized (_remove) {
                _remove.add(listener);
            }
        }

    }

}
