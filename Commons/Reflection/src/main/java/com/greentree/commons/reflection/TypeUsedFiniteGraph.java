package com.greentree.commons.reflection;

import com.greentree.commons.graph.FiniteGraph;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeInfoBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public record TypeUsedFiniteGraph(Iterable<? extends TypeInfo<?>> classes) implements FiniteGraph<TypeInfo<?>> {

    private static final long serialVersionUID = 1L;

    @Override
    public Iterable<? extends TypeInfo<?>> getAdjacencyIterable(Object v) {
        var cls = (TypeInfo<?>) v;
        var result = new HashSet<TypeInfo<?>>();
        var superCls = cls.getSuperType();
        if (superCls != null)
            result.add(superCls);
        Collections.addAll(result, cls.getInterfaces());
        for (var f : cls.toClass().getDeclaredFields())
            result.add(TypeInfoBuilder.getTypeInfo(f.getGenericType()));
        for (var method : cls.toClass().getDeclaredMethods()) {
            result.add(TypeInfoBuilder.getTypeInfo(method.getGenericReturnType()));
            for (var p : method.getParameters())
                result.add(TypeInfoBuilder.getTypeInfo(p.getParameterizedType()));
        }
        for (var constructor : cls.toClass().getDeclaredConstructors())
            for (var p : constructor.getParameters())
                result.add(TypeInfoBuilder.getTypeInfo(p.getParameterizedType()));
        result.remove(cls);
        return result.stream().filter(x -> x != null).filter(x -> !x.isPrimitive()).toList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<TypeInfo<?>> iterator() {
        return (Iterator<TypeInfo<?>>) classes.iterator();
    }

}
