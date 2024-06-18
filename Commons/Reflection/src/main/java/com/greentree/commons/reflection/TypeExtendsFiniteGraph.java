package com.greentree.commons.reflection;

import com.greentree.commons.graph.FiniteGraph;

import java.util.Iterator;
import java.util.stream.StreamSupport;

public record TypeExtendsFiniteGraph(Iterable<Class<?>> classes) implements FiniteGraph<Class<?>> {

    private static final long serialVersionUID = 1L;

    @Override
    public Iterable<? extends Class<?>> getAdjacencyIterable(Object v) {
        var cls = (Class<?>) v;
        var result = StreamSupport.stream(spliterator(), false)
                .filter(x ->
                {
                    if (x.equals(cls.getSuperclass()) || cls.equals(x.getSuperclass()))
                        return true;
                    for (var i : x.getInterfaces())
                        if (i.equals(cls))
                            return true;
                    for (var i : cls.getInterfaces())
                        if (i.equals(x))
                            return true;
                    return false;
                }).toList();
        return result;
    }

    @Override
    public Iterator<Class<?>> iterator() {
        return classes.iterator();
    }

}
