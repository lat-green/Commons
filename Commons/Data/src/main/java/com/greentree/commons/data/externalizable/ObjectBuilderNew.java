package com.greentree.commons.data.externalizable;

import static com.greentree.commons.reflection.ObjectBuilder.*;
import static com.greentree.commons.reflection.info.TypeInfoBuilder.*;
import static java.util.Arrays.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.greentree.commons.reflection.ClassUtil;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.util.cortege.Pair;
import com.greentree.commons.util.cortege.Triple;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.commons.util.function.CheckedFunction;

public class ObjectBuilderNew {

	public static <T> T get(Class<T> type, Collection<String> names, CheckedFunction<Triple<TypeInfo<?>, String, Object>, Object> func) throws Exception {
		final T obj = newInstanse(type, names,
				pair -> func.apply(new Triple<>(getTypeInfo(pair.first), pair.seconde, null)));

		if(names.isEmpty()) return obj;

		final Map<String, Field> map = new HashMap<>();
		for(var f : ClassUtil.getAllNotStaticFields(type)) map.put(f.getName(), f);

		names.parallelStream().map(n -> map.get(n)).forEach(f -> {
			if(f == null) return;
			boolean access = f.canAccess(obj);
			try {
				f.setAccessible(true);
				var value = func.apply(new Triple<>(getTypeInfo(f.getGenericType()), f.getName(), f.get(obj)));
				f.set(obj, value);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				f.setAccessible(access);
			}
		});

		return obj;
	}

	private static <T> T newInstanse(Class<T> type, Collection<String> names, CheckedFunction<Pair<Class<?>, String>, Object> func) throws Exception {
		var c = getMaxConstructor(type, names);
		if(c == null)c = getMinConstructor(type);
		if(c == null)throw new UnsupportedOperationException(type + " not have constructor(maybe abstract)");
		Object[] args = new Object[c.getParameterCount()];
		try {
			{
				Parameter[] params = c.getParameters();
				for(int i = 0; i < params.length; i++) {
					var p = params[i];
					args[i] = func.apply(new Pair<>(p.getType(), p.getName()));
					names.remove(p.getName());
				}
			}
			return c.newInstance(args);
		}catch (InvocationTargetException e) {
			throw new MultiException("args: " + asList(args) + " constructor: " + asList(c.getParameters()).stream().map(Parameter::getName).collect(Collectors.toList()) + " type:" + type, e.getCause());
		}catch(Exception e) {
			throw new MultiException("args: " + asList(args) + " constructor: " + asList(c.getParameters()).stream().map(Parameter::getName).collect(Collectors.toList()) + " type:" + type, e);
		}
	}

}
