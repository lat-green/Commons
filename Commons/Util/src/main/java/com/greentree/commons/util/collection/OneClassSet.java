package com.greentree.commons.util.collection;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
/** @author Arseny Latyshev */
public class OneClassSet<E> extends AbstractSet<E> implements Externalizable {

    private static final long serialVersionUID = 1L;
    private final Map<Class<? extends E>, E> map;

    public OneClassSet() {
        map = new ConcurrentHashMap<>();
    }

    public OneClassSet(Collection<? extends E> collection) {
        this(collection.size());
        addAll(collection);
    }

    public OneClassSet(final int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    public OneClassSet(final Map<? extends Class<? extends E>, ? extends E> m) {
        map = new HashMap<>(m);
    }

    public OneClassSet(OneClassSet<? extends E> cs) {
        map = new HashMap<>(cs.map);
    }

    @SuppressWarnings("unchecked")
    public <T extends E> T get(Class<T> clazz) {
        return (T) map.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public <T extends E> Optional<T> getOptional(Class<T> clazz) {
        return Optional.ofNullable((T) map.get(clazz));
    }

    @Override
    public Iterator<E> iterator() {
        return map.values().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        if (e == null)
            return false;
        final Class<? extends E> cls = (Class<? extends E>) e.getClass();
        if (!containsClass(cls)) {
            map.put(cls, e);
            return true;
        }
        return false;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(map.size());
        for (var c : this)
            out.writeObject(c);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        var size = in.readInt();
        while (size-- > 0) {
            final var c = (E) in.readObject();
            add(c);
        }
    }

    public boolean containsClass(Class<? extends E> o) {
        return map.containsKey(o);
    }

}
