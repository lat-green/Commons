package com.greentree.commons.util.classes.info;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

import com.greentree.commons.util.function.LambdaSaveFunction;
import com.greentree.commons.util.function.SaveFunction;
import com.greentree.commons.util.iterator.IteratorUtil;

public abstract class TypeInfoBuilder {
	
	private final static SaveFunction<ParameterizedType, TypeInfo<?>> parameterizedTypeMap = new LambdaSaveFunction<>(
			TypeInfoBuilder::getTypeInfo0);
	
	private TypeInfoBuilder() {
	}
	
	public static <C> ClassInfo<C> getTypeInfo(Class<C> cls) {
		return ClassInfo.get(cls);
	}
	
	public static <C> TypeInfo<C> getTypeInfo(Field field) {
		if(field == null)
			return null;
		return getTypeInfo(field.getGenericType());
	}
	
	@SuppressWarnings("unchecked")
	public static <C> ClassInfo<C> getTypeInfo(Object obj) {
		if(obj == null)
			return null;
		final Class<C> c = (Class<C>) obj.getClass();
		return getTypeInfo(c);
	}
	
	@SuppressWarnings("unchecked")
	public static <C> TypeInfo<C> getTypeInfo(ParameterizedType type) {
		return (TypeInfo<C>) parameterizedTypeMap.apply(type);
	}
	
	public static <C> TypeInfo<C> getTypeInfo(GenericArrayType type) {
		return null;
	}
	
	public static <C> TypeInfo<C> getTypeInfo(TypeVariable<?> type) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <C> TypeInfo<C> getTypeInfo(Type type) {
		if(type == null)
			return null;
		
		if(type instanceof Class)
			return getTypeInfo((Class<C>) type);
		
		if(type instanceof ParameterizedType)
			return getTypeInfo((ParameterizedType) type);
		
		if(type instanceof GenericArrayType)
			return getTypeInfo((GenericArrayType) type);
		
		if(type instanceof TypeVariable)
			return getTypeInfo((TypeVariable<?>) type);
		
		if(type instanceof WildcardType) {
			final var w = (WildcardType) type;
			if(w.getLowerBounds().length == 0 && w.getUpperBounds().length == 1)
				return getTypeInfo(w.getUpperBounds()[0]);
			throw new UnsupportedOperationException(Arrays.toString(w.getLowerBounds()) + " "
					+ Arrays.toString(w.getUpperBounds()));
		}
		
		throw new UnsupportedOperationException(
				"type mast by instance of ParameterizedType or Class " + type + " "
						+ type.getClass());
	}
	
	public static <C> TypeInfo<C> getTypeInfo(Class<?> type, Object... parameteris) {
		final var types = new Type[parameteris.length];
		for(int i = 0; i < parameteris.length; i++) {
			final var p = parameteris[i];
			if(p instanceof Type)
				types[i] = (Type) p;
			else
				if(p instanceof TypeInfo)
					types[i] = ((TypeInfo<?>) p).getType();
				else
					throw new IllegalArgumentException(p + "");
		}
		return getTypeInfo(type, types);
	}
	
	public static <C> TypeInfo<C> getTypeInfo(Class<?> type, TypeInfo<?>... parameteris) {
		final var types = new Type[parameteris.length];
		for(int i = 0; i < parameteris.length; i++) {
			final var p = parameteris[i];
			types[i] = ((TypeInfo<?>) p).getType();
		}
		return getTypeInfo(type, types);
	}
	
	public static <C> TypeInfo<C> getTypeInfo(Class<?> type, Type... parameteris) {
		return getTypeInfo(GenericType.build(type, parameteris));
	}
	
	private static <C> TypeInfo<C> getTypeInfo0(ParameterizedType type) {
		final var args = IteratorUtil
				.clone(IteratorUtil.map(IteratorUtil.iterable(type.getActualTypeArguments()), s->
				{
					return getTypeInfo(s);
				}));
		if(IteratorUtil.all(args, i->i == null)) {
			return getTypeInfo(type.getRawType());
		}
		if(IteratorUtil.all(args, i->i != null)) {
			return ParameterizedTypeInfo.get(type);
		}
		throw new IllegalArgumentException("" + type);
	}
	
}
