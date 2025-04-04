package com.greentree.commons.action.container;

import com.greentree.commons.util.iterator.IteratorUtil;

import java.lang.ref.WeakReference;
import java.util.*;

public class WeakMultiSet<E> extends AbstractCollection<E> {

    private final ArrayList<WeakReference<E>> refereces;

    public WeakMultiSet(int initialCapacity) {
        refereces = new ArrayList<>(initialCapacity);
    }

    public WeakMultiSet() {
        refereces = new ArrayList<>();
    }

    private final class ReferenceWrapper implements Iterator<E> {

        private final Collection<E> anchor = new ArrayList<>();//save value from gc

        private final Iterator<WeakReference<E>> iter;

        public ReferenceWrapper(Iterable<WeakReference<E>> iter) {
            final var iter1 = iter.iterator();
            while (iter1.hasNext()) {
                final var ref = iter1.next();
                final var v = ref.get();
                if (v == null) {
                    iter1.remove();
                    weakRemove();
                }
                anchor.add(v);
            }
            this.iter = iter.iterator();
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public E next() {
            return iter.next().get();
        }

        @Override
        public void remove() {
            iter.remove();
        }

    }    @Override
    public synchronized boolean add(E e) {
        Objects.requireNonNull(e);
        return refereces.add(new WeakReference<>(e));
    }

    protected void weakRemove() {
    }    @Override
    public synchronized Iterator<E> iterator() {
        return new ReferenceWrapper(refereces);
    }

    @Override
    public int size() {
        return IteratorUtil.size(iterator());
    }





}
