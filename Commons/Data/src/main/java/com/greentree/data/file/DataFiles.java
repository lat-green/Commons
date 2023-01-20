package com.greentree.data.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ServiceLoader;

import com.greentree.common.util.classes.ClassUtil;
import com.greentree.common.util.classes.info.TypeInfo;
import com.greentree.common.util.classes.info.TypeInfoBuilder;
import com.greentree.common.util.classes.info.TypeUtil;
import com.greentree.common.util.cortege.Pair;
import com.greentree.common.util.exception.MultiException;
import com.greentree.common.util.iterator.IteratorUtil;

public class DataFiles {
	
	public static final int NULL = 0;
	
	private static final Map<TypeInfo<?>, DataSerializer> map = new HashMap<>();
	
	private static final Iterable<? extends DataSerializer> serializers = ServiceLoader
			.load(DataSerializer.class);
	
	static {
		for(var s : serializers)
			map.put(s.root(), s);
	}
	
	public static Iterable<? extends TypeInfo<?>> getAllSuperTypes(TypeInfo<?> type) {
		final var res = new HashSet<TypeInfo<?>>();
		for(var i : TypeUtil.getSuperClassAndInterfaces(type)) {
			res.add(i);
			final var s = getAllSuperTypes(i);
			for(var e : s)
				res.add(e);
		}
		return res;
	}
	
	private static Iterable<? extends DataSerializer> generateSuperAndInterface(TypeInfo<?> type) {
		final var res = new HashSet<DataSerializer>();
		for(var i : getAllSuperTypes(type))
			if(map.containsKey(i))
				res.add(map.get(i));
		for(var s : map.values())
			if(ClassUtil.isExtends(s.root().toClass(), type.toClass()))
				res.add(s);
		return res;
	}
	
	public static DataSerializer getSerializer(TypeInfo<?> type) {
		if(map.containsKey(type))
			return map.get(type);
		final var ss = generateSuperAndInterface(type);
		final var s = IteratorUtil.min(ss, p->p.priority(type));
		map.put(type, s);
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Pair<T, Runnable> get(T last, DataFileObject faobj, DataFileReader file) {
		try {
			final var serializer = DataFiles.getSerializer(faobj.type());
			try(final var bin = new ByteArrayInputStream(faobj.data())) {
				try(final var in = new DataInputStream(bin)) {
					return (Pair<T, Runnable>) serializer.read(last, faobj.type(), in, file);
				}
			}
		}catch(Exception e) {
			throw new MultiException("can not delta read " + faobj, e);
		}
	}
	
	public static Pair<Object, Runnable> get(DataFileObject faobj, DataFileReader file) {
		return get(faobj.type(), faobj.data(), file);
	}
	
	public static Pair<Object, Runnable> get(TypeInfo<?> type, byte[] data, DataFileReader file) {
		final var serializer = DataFiles.getSerializer(type);
		try {
			try(final var bin = new ByteArrayInputStream(data)) {
				try(final var in = new DataInputStream(bin)) {
					return serializer.read(type, in, file);
				}
			}
		}catch(IOException e) {
			throw new IllegalArgumentException("can not delta read " + type, e);
		}
	}
	
	public static DataFileObject write(Object obj, DataFileWriter file) {
		final var cls = TypeInfoBuilder.getTypeInfo(obj);
		final var serializer = DataFiles.getSerializer(cls);
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var out = new DataOutputStream(bout)) {
				serializer.write(obj, out, file);
			}
			return new DataFileObject(cls, bout.toByteArray());
		}catch(IllegalArgumentException e) {
			throw e;
		}catch(Exception e) {
			throw new IllegalArgumentException("" + obj, e);
		}
	}
	
	public static <T> DataFileObject write(T last, T obj, DataFileWriter file) {
		final var cls = TypeInfoBuilder.getTypeInfo(obj);
		final var serializer = DataFiles.getSerializer(cls);
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var out = new DataOutputStream(bout)) {
				serializer.write(last, obj, out, file);
			}
			final var afobj = new DataFileObject(cls, bout.toByteArray());
			return afobj;
		}catch(IllegalArgumentException e) {
			throw e;
		}catch(Exception e) {
			throw new IllegalArgumentException("" + obj, e);
		}
	}
	
}
