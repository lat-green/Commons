package com.greentree.commons.reflection.info;

import java.io.Serializable;
import java.lang.reflect.Type;

public interface TypeInfo<C> extends Serializable {

    default <T> TypeInfo<T> asExtends(TypeInfo<T> type) {
        return TypeInfoBuilder.getTypeInfo(toClass(), type.getTypeArguments());
    }

    Class<C> toClass();

    TypeInfo<?>[] getTypeArguments();

    TypeInfo<? super C>[] getInterfaces();

    TypeInfo<C> getBoxing();

    TypeInfo<? super C> getSuperType();

    String getSimpleName();

    String getName();

    default boolean isPrimitive() {
        return toClass().isPrimitive();
    }

    default boolean isInterface() {
        return toClass().isInterface();
    }

    default boolean isSuperOf(TypeInfo<? super C> superType) {
        return superType.isSuperTo(this);
    }

    boolean isSuperTo(TypeInfo<? extends C> type);

    default CharSequence getTypeName() {
        return getType().getTypeName();
    }

    Type getType();

    default boolean isInstance(Object obj) {
        return toClass().isInstance(obj);
    }

}
