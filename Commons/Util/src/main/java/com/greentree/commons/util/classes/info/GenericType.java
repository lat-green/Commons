package com.greentree.commons.util.classes.info;

import java.io.Serializable;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;

import com.greentree.commons.util.function.LambdaSaveBiFunction;

public final class GenericType implements ParameterizedType, Serializable {
	
	private final static BiFunction<Class<?>, Type[], GenericType> genericType = new LambdaSaveBiFunction<>(
			(t, s)->new GenericType(t, s));
	private static final long serialVersionUID = 1L;
	
	private final Type[] actualTypeArguments;
	
	private final Class<?> rawType;
	
	private final Type ownerType;
	
	private GenericType(Class<?> rawType, Type[] actualTypeArguments) {
		this(rawType, actualTypeArguments, null);
	}
	
	private GenericType(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
		this.actualTypeArguments = actualTypeArguments;
		this.rawType = rawType;
		this.ownerType = ownerType != null ? ownerType : rawType.getDeclaringClass();
		validateConstructorArguments();
	}
	
	public static GenericType build(Class<?> type) {
		return genericType.apply(type, new Type[0]);
	}
	
	public static GenericType build(Class<?> type, Type... parameteris) {
		Objects.requireNonNull(parameteris);
		return genericType.apply(type, parameteris);
	}
	
	public static GenericType make(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
		return new GenericType(rawType, actualTypeArguments, ownerType);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof ParameterizedType) {
			ParameterizedType that = (ParameterizedType) o;
			
			if(this == that)
				return true;
			
			Type thatOwner = that.getOwnerType();
			Type thatRawType = that.getRawType();
			
			return Objects.equals(ownerType, thatOwner) && Objects.equals(rawType, thatRawType)
					&& Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
		}else
			return false;
	}
	
	@Override
	public Type[] getActualTypeArguments() {
		return actualTypeArguments.clone();
	}
	
	@Override
	public Type getOwnerType() {
		return ownerType;
	}
	
	
	@Override
	public Class<?> getRawType() {
		return rawType;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(actualTypeArguments) ^ Objects.hashCode(ownerType)
				^ Objects.hashCode(rawType);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(ownerType != null) {
			sb.append(ownerType.getTypeName());
			
			sb.append("$");
			
			if(ownerType instanceof GenericType)
				sb.append(rawType.getName()
						.replace(((GenericType) ownerType).rawType.getName() + "$", ""));
			else
				sb.append(rawType.getSimpleName());
		}else
			sb.append(rawType.getName());
		
		if(actualTypeArguments != null) {
			StringJoiner sj = new StringJoiner(", ", "<", ">");
			sj.setEmptyValue("");
			for(Type t : actualTypeArguments)
				sj.add(t.getTypeName());
			sb.append(sj.toString());
		}
		
		return sb.toString();
	}
	
	private void validateConstructorArguments() {
		TypeVariable<?>[] formals = rawType.getTypeParameters();
		if(formals.length != actualTypeArguments.length)
			throw new MalformedParameterizedTypeException(String.format(
					"Mismatch of count of " + "formal and actual type "
							+ "arguments in constructor " + "of %s: %d formal argument(s) "
							+ "%d actual argument(s)",
					rawType.getName(), formals.length, actualTypeArguments.length));
	}
}
