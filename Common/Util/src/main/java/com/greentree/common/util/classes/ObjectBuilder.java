package com.greentree.common.util.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.greentree.common.util.classes.info.TypeInfo;
import com.greentree.common.util.classes.info.TypeInfoBuilder;
import com.greentree.common.util.collection.FunctionAutoGenerateMap;
import com.greentree.common.util.cortege.Pair;
import com.greentree.common.util.cortege.Triple;
import com.greentree.common.util.function.CheckedFunction;

public abstract class ObjectBuilder {
	
	private ObjectBuilder() {
	}
	
	public static <T, I> T get(Class<T> type, Map<String, I> arguments,
			CheckedFunction<Triple<TypeInfo<?>, Object, I>, Object> func) throws Exception {
		return get(type, arguments, new HashMap<>(), func);
	}
	
	public static <T, I> T get(Class<T> type, Map<String, I> arguments, Map<String, Object> build,
			CheckedFunction<Triple<TypeInfo<?>, Object, I>, Object> func) throws Exception {
		final T obj = newInstanse(type, arguments, build, pair->func
				.apply(new Triple<>(TypeInfoBuilder.getTypeInfo(pair.first), null, pair.seconde)));
		var names = new ArrayList<>(arguments.keySet());
		for(var n : names)
			try {
				Field f = type.getDeclaredField(n);
				var data = arguments.remove(n);
				
				boolean access = f.canAccess(obj);
				f.setAccessible(true);
				try {
					var value = build.get(f.getName());
					if(value == null)
						value = func.apply(new Triple<>(
								TypeInfoBuilder.getTypeInfo(f.getGenericType()), f.get(obj), data));
					if(value != null)
						f.set(obj, value);
				}finally {
					f.setAccessible(access);
				}
			}catch(NoSuchFieldException e) {
				e.printStackTrace();
			}
		return obj;
	}
	
	public static Map<String, Object> getArgumentsMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if(ClassUtil.isPrimitive(obj.getClass()))
			return map;
		for(var f : ClassUtil.getAllFields(obj.getClass())) {
			var m = f.getModifiers();
			if(!(Modifier.isStatic(m) | Modifier.isTransient(m))) {
				boolean access = f.canAccess(obj);
				f.setAccessible(true);
				try {
					map.put(f.getName(), f.get(obj));
				}catch(IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}finally {
					f.setAccessible(access);
				}
			}
		}
		return map;
	}
	
	public static Map<String, Set<TypeInfo<?>>> getBuildMap(Class<?> clazz) {
		var map = new FunctionAutoGenerateMap<String, Set<TypeInfo<?>>>(str->new HashSet<>());
		if(ClassUtil.isPrimitive(clazz))
			return map;
		for(var constructor : clazz.getConstructors())
			for(var p : constructor.getParameters()) {
				var cl = p.getParameterizedType();
				if(cl instanceof Class)
					map.get(p.getName()).add(TypeInfoBuilder.getTypeInfo((Class<?>) cl));
				if(cl instanceof ParameterizedType)
					map.get(p.getName()).add(TypeInfoBuilder.getTypeInfo((ParameterizedType) cl));
			}
		for(var f : ClassUtil.getAllFields(clazz)) {
			var m = f.getModifiers();
			if(!(Modifier.isStatic(m) | Modifier.isTransient(m))) {
				var cl = f.getGenericType();
				if(cl instanceof ParameterizedType)
					map.get(f.getName()).add(TypeInfoBuilder.getTypeInfo((ParameterizedType) cl));
				else
					map.get(f.getName()).add(TypeInfoBuilder.getTypeInfo(f.getType()));
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getMaxConstructor(Class<T> clazz, Collection<String> names) {
		Constructor<?> constructor = null;
		A :
		for(Constructor<?> c : clazz.getConstructors()) {
			for(var p : c.getParameters())
				if(!names.contains(p.getName()))
					continue A;
			if(constructor == null)
				constructor = c;
			if(constructor.getParameterCount() < c.getParameterCount())
				constructor = c;
		}
		return (Constructor<T>) constructor;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getMinConstructor(Class<T> clazz) {
		Constructor<?> constructor = null;
		for(Constructor<?> c : clazz.getConstructors()) {
			if(constructor == null)
				constructor = c;
			if(constructor.getParameterCount() > c.getParameterCount())
				constructor = c;
		}
		return (Constructor<T>) constructor;
	}
	
	public static Parameter getParameter(Constructor<?> constructor, String name) {
		for(var p : constructor.getParameters())
			if(name.equals(p.getName()))
				return p;
		return null;
	}
	
	public static <T, I> T newInstanse(Class<T> type, Map<String, I> mapTOBuild,
			CheckedFunction<Pair<Class<?>, I>, Object> func) throws Exception {
		return newInstanse(type, mapTOBuild, new HashMap<>(), func);
	}
	
	public static <T, I> T newInstanse(Class<T> type, Map<String, I> mapTOBuild,
			Map<String, Object> build, CheckedFunction<Pair<Class<?>, I>, Object> func)
			throws Exception {
		var names = new ArrayList<>(mapTOBuild.keySet());
		var c = getMaxConstructor(type, names);
		if(c == null)
			c = getMinConstructor(type);
		if(c == null)
			throw new UnsupportedOperationException(type + " not have constructor(maybe abstract)");
		Object[] args = new Object[c.getParameterCount()];
		try {
			{
				Parameter[] params = c.getParameters();
				for(int i = 0; i < params.length; i++) {
					var p = params[i];
					var r = build.get(p.getName());
					if(r == null) {
						Pair<Class<?>, I> p0 = new Pair<>(p.getType(),
								mapTOBuild.remove(p.getName()));
						r = func.apply(p0);
					}
					args[i] = r;
				}
			}
			return c.newInstance(args);
		}catch(Exception e) {
			throw new UnsupportedOperationException("args: " + Arrays.toString(args)
					+ " constructor: " + Arrays.asList(c.getParameters()).stream()
							.map(Parameter::getName).collect(Collectors.toList())
					+ " type:" + type, e);
		}
	}
	
}
