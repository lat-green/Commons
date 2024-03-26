package com.greentree.commons.reflection.info;

import com.greentree.commons.util.iterator.IteratorUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class TypeUtil {

    private static final TypeInfo<Object> OBJECT_TYPE = TypeInfoBuilder.getTypeInfo(Object.class);

    public static boolean isExtends(Class<?> superType, TypeInfo<?> type) {
        return isExtends(TypeInfoBuilder.getTypeInfo(superType), type);
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean isExtends(TypeInfo<T> superType, TypeInfo<?> type) {
        if (superType.equals(type))
            return true;
        return superType.isSuperTo((TypeInfo<? extends T>) type);
    }

    public static boolean isExtends(TypeInfo<?> superType, Class<?> type) {
        return isExtends(superType, TypeInfoBuilder.getTypeInfo(type));
    }

    public static boolean isExtends(Class<?> superType, Class<?> type) {
        return isExtends(TypeInfoBuilder.getTypeInfo(superType), TypeInfoBuilder.getTypeInfo(type));
    }

    public static <T> TypeInfo<?> lca(Iterable<? extends TypeInfo<? extends T>> iter) {
        return IteratorUtil.reduce(iter, (a, b) -> lca(a, b), () -> OBJECT_TYPE);
    }

    public static <T> TypeInfo<?> lca(TypeInfo<? extends T> a, TypeInfo<? extends T> b) {
        if (isExtends(a, b))
            return a;
        if (isExtends(b, a))
            return b;
        final var lcas = new HashSet<TypeInfo<?>>();
        for (var i : getSuperClassAndInterfaces(a)) {
            final var lca = lca(i, b);
            lcas.add(lca);
        }
        for (var i : getSuperClassAndInterfaces(b)) {
            final var lca = lca(a, i);
            lcas.add(lca);
        }
        lcas.remove(OBJECT_TYPE);
        if (lcas.isEmpty())
            return OBJECT_TYPE;
        if (lcas.size() == 1)
            return lcas.iterator().next();
        for (var lca : lcas)
            if (!lca.isInterface())
                return lca;
        throw new IllegalArgumentException("lcas " + lcas + " of " + a + " " + b);
    }

    public static <T, S> TypeInfo<T> getFirstArgument(Class<? extends S> type,
                                                      Class<S> superClass) {
        return getFirstArgument(TypeInfoBuilder.getTypeInfo(type), superClass);
    }

    @SuppressWarnings("unchecked")
    public static <T, S> TypeInfo<T> getFirstArgument(TypeInfo<? extends S> type,
                                                      Class<S> superClass) {
        return (TypeInfo<T>) getType(type, superClass).getTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeInfo<T> getType(TypeInfo<? extends T> type, Class<T> superClass) {
        if (type.toClass().equals(superClass))
            return (TypeInfo<T>) type;
        final var superType = TypeInfoBuilder.getTypeInfo(superClass);
        for (var i : getSuperClassAndInterfaces(type))
            if (isExtends(superType, i)) {
                final TypeInfo<T> t = getType((TypeInfo<? extends T>) i, superClass);
                if (t != null)
                    return t;
            }
        return null;
    }

    public static <T> Iterable<TypeInfo<? super T>> getSuperClassAndInterfaces(TypeInfo<T> info) {
        final Collection<TypeInfo<? super T>> res = new ArrayList<>();
        final var s = info.getSuperType();
        if (s != null)
            res.add(s);
        Collections.addAll(res, info.getInterfaces());
        return res;
    }

}
