package com.greentree.commons.util.classes.info;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.function.AbstractSaveFunction;
import com.greentree.commons.util.iterator.IteratorUtil;

public final class ParameterizedTypeInfo<C> implements TypeInfo<C> {
	
	private final static ParameterizedTypeInfoSingletonFactory INSTANCIES = new ParameterizedTypeInfoSingletonFactory();
	
	private static final long serialVersionUID = 1L;
	
	private final ParameterizedType type;
	
	private ParameterizedTypeInfo(ParameterizedType type) {
		this.type = type;
	}
	
	public static <C> ParameterizedTypeInfo<C> get(ParameterizedType type) {
		return INSTANCIES.get(type);
	}
	
	private static boolean dfsTo(ClassInfo<?> type, ParameterizedTypeInfo<?> superType) {
		if(!ClassUtil.isExtends(superType.toClass(), type.toClass()))
			return false;
		
		for(var s : TypeUtil.getSuperClassAndInterfaces(type))
			if(dfsTo(s, superType))
				return true;
		return false;
	}
	
	private static boolean dfsTo(ParameterizedTypeInfo<?> type,
			ParameterizedTypeInfo<?> superType) {
		if(!ClassUtil.isExtends(superType.toClass(), type.toClass()))
			return false;
		
		if(superType.equals(type))
			return true;
		
		for(var s : TypeUtil.getSuperClassAndInterfaces(type))
			if(dfsTo(s, superType))
				return true;
		return false;
	}
	
	private static boolean dfsTo(TypeInfo<?> type, ParameterizedTypeInfo<?> superType) {
		if(type instanceof ParameterizedTypeInfo)
			return dfsTo((ParameterizedTypeInfo<?>) type, superType);
		if(type instanceof ClassInfo)
			return dfsTo((ClassInfo<?>) type, superType);
		return false;
	}
	
	private static Type map(Type type, Map<String, Type> map) {
		if(type instanceof ParameterizedType) {
			final var p_sup = (ParameterizedType) type;
			
			final var types_list = Arrays.asList(p_sup.getActualTypeArguments()).stream().map(t-> {
				if(t instanceof TypeVariable)
					return map.get(((TypeVariable<?>) t).getName());
				if(t instanceof ParameterizedType)
					return map(t, map);
				return null;
			}).collect(Collectors.toList());
			
			final var types = new Type[types_list.size()];
			types_list.toArray(types);
			
			final var raw = p_sup.getRawType();
			return GenericType.build((Class<?>) raw, types);
		}
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		ParameterizedTypeInfo<?> other = (ParameterizedTypeInfo<?>) obj;
		return Objects.equals(type, other.type);
	}
	
	@Override
	public TypeInfo<C> getBoxing() {
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TypeInfo<? super C>[] getInterfaces() {
		final var clazz = toClass();
		final var map = getParametersMap();
		
		
		final var is = clazz.getGenericInterfaces();
		final TypeInfo<? super C>[] ts = new TypeInfo[is.length];
		
		for(var i = 0; i < is.length; i++)
			ts[i] = TypeInfoBuilder.getTypeInfo(map(is[i], map));
		
		return ts;
	}
	
	public ParameterizedType getParameterizedType() {
		return type;
	}
	
	public Map<String, Type> getParametersMap() {
		final var map = new HashMap<String, Type>();
		{
			final var names = toClass().getTypeParameters();
			final var types = type.getActualTypeArguments();
			
			final var len = names.length;
			assert len == types.length;
			
			for(var i = 0; i < len; i++)
				map.put(names[i].getName(), types[i]);
		}
		return map;
	}
	
	public TypeInfo<C> getRawType() {
		return TypeInfoBuilder.getTypeInfo(type.getRawType());
	}
	
	@Override
	public TypeInfo<? super C> getSuperType() {
		final var clazz = toClass();
		final var sup = clazz.getGenericSuperclass();
		return TypeInfoBuilder.getTypeInfo(map(sup));
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public TypeInfo<?>[] getTypeArguments() {
		final var args = type.getActualTypeArguments();
		final var res = new TypeInfo[args.length];
		for(var i = 0; i < args.length; i++)
			res[i] = TypeInfoBuilder.getTypeInfo(args[i]);
		return res;
	}
	
	@Override
	public CharSequence getTypeName() {
		return type.getTypeName();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
	
	@Override
	public boolean isSuper(TypeInfo<? super C> superType) {
		if(superType instanceof ParameterizedTypeInfo) {
			final var s = (ParameterizedTypeInfo<? super C>) superType;
			if(ClassUtil.isExtends(s.toClass(), toClass()))
				return dfsTo(this, s);
			return false;
		}
		return superType.toClass().isAssignableFrom(toClass());
	}
	
	@Override
	public boolean isSuperTo(TypeInfo<? extends C> type) {
		return dfsTo(type, this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<C> toClass() {
		return (Class<C>) type.getRawType();
	}
	
	@Override
	public String toString() {
		return "ParameterizedTypeInfo [" + type + "]";
	}
	
	private Type map(Type type) {
		return map(type, getParametersMap());
	}
	
	private static final class ParameterizedTypeInfoSingletonFactory
			extends AbstractSaveFunction<ParameterizedType, ParameterizedTypeInfo<?>> {
		
		
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("unchecked")
		public <C> ParameterizedTypeInfo<C> get(ParameterizedType type) {
			return (ParameterizedTypeInfo<C>) applyRaw(type);
		}
		
		@Override
		protected ParameterizedTypeInfo<?> create(ParameterizedType type) {
			final var args = IteratorUtil
					.clone(IteratorUtil.map(IteratorUtil.iterable(type.getActualTypeArguments()),
							TypeInfoBuilder::getTypeInfo));
			if(IteratorUtil.all(args, i->i != null))
				return new ParameterizedTypeInfo<>(type);
			throw new IllegalArgumentException("" + type);
		}
		
	}
	
}
