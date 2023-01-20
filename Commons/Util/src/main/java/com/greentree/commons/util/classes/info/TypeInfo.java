package com.greentree.commons.util.classes.info;

import java.io.Serializable;
import java.lang.reflect.Type;

public interface TypeInfo<C> extends Serializable {
	
	TypeInfo<?>[] getTypeArguments();
	
	TypeInfo<? super C>[] getInterfaces();
	TypeInfo<?> getBoxing();
	TypeInfo<? super C> getSuperType();
	
	Class<C> toClass();
	
	Type getType();
	
	default boolean isPrimitive() {
		return toClass().isPrimitive();
	}
	
	default boolean isInterface() {
		return toClass().isInterface();
	}
	
	boolean isSuperTo(TypeInfo<? extends C> type);
	
	default boolean isSuper(TypeInfo<? super C> superType) {
		return superType.isSuperTo(this);
	}
	
	default CharSequence getTypeName() {
		return getType().getTypeName();
	}
	
	default boolean isInstance(Object obj) {
		return toClass().isInstance(obj);
	}
	
}
