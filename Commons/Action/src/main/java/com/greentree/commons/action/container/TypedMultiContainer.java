package com.greentree.commons.action.container;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.util.collection.FunctionAutoGenerateMap;
import com.greentree.commons.util.iterator.IteratorUtil;

import java.util.Map;

public class TypedMultiContainer<T, L> implements TypedListenerContainer<T, L> {

    private static final long serialVersionUID = 1L;

    private transient final Map<T, MultiContainer<L>> type_containers = new FunctionAutoGenerateMap<>(
            type -> new MultiContainer<>());
    private transient final MultiContainer<L> notype_listeners = new MultiContainer<>();

    @Override
    public ListenerCloser add(L listener) {
        return notype_listeners.add(listener);
    }

    @Override
    public ListenerCloser add(T type, L listener) {
        return type_containers.get(type).add(listener);
    }

    @Override
    public int size(T type) {
        return notype_listeners.size() + type_containers.size();
    }

    @Override
    public Iterable<? extends L> listeners(T type) {
        return IteratorUtil.union(type_containers.get(type), notype_listeners);
    }

    @Override
    public void clear() {
        notype_listeners.clear();
        for (var c : type_containers.values())
            c.clear();
        type_containers.clear();
    }

}
