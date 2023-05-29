package com.greentree.commons.reflection;

import static com.greentree.commons.reflection.ClassUtil.*;
import static java.lang.reflect.Modifier.*;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import com.greentree.commons.util.array.proxy.ArrayProxy;

public class SerializationVerifier {

	public static boolean isSerializabeClass(Class<?> clazz) {
		if(isPrimitive(clazz)) return true;
		if(!isExtends(Serializable.class, clazz)) return false;
		if(clazz.isArray()) return isSerializabeClass(clazz.componentType());

		for(var f : getAllFields(clazz)) if(!isSerializabeField(f)) return false;

		return true;
	}

	public static boolean isSerializabeField(Field field) {
		final var m = field.getModifiers();
		return isStatic(m) || isTransient(m) || isSerializabeClass(field.getType());
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean isSerializabeObject(T obj) {
		if(obj == null) throw new NullPointerException("obj is null (try use isSerializabeObject(Object obj, Class cls))");
		final Class<T> clazz = (Class<T>) obj.getClass();
		return isSerializabeObject(obj, clazz);
	}

	public static <T> boolean isSerializabeObject(T obj, Class<T> cls) {
		if(obj == null) return isSerializabeClass(cls);

		if(cls.isArray()) {
			final var array = ArrayProxy.build(obj);
			final var component = cls.componentType();

			if(!isSerializabeClass(cls)) return false;

			for(int i = 0; i < array.length(); i++) if(!isSerializabeObjectField(array.get(i), component)) return false;

			return true;
		}

		//		try {
		//			final var fs = getAllFields(clazz);
		//			for(var f : fs) try {
		//				if(!isSerializabeObjectField(obj, f)) return false;
		//			}catch(Throwable e1) {
		//				try {
		//					if(isSerializabeObjectTryCatch(obj, f)) return false;
		//				}catch(Throwable e2) {
		//					throw new MultyException(e1, e2);
		//				}
		//			}
		//			return true;
		//		}catch(Exception e) {
		//		}
		return isSerializabeObjectTryCatch(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean isSerializabeObjectField(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException {
		final Class<T> c = (Class<T>) field.getType();
		final T t = (T) field.get(obj);
		return isSerializabeObject(t, c);
	}

	@SuppressWarnings("unchecked")
	private static <T> boolean isSerializabeObjectField(Object obj, Class<?> component) {
		if(obj == null)
			return isSerializabeObject(null, component);
		final Class<T> clazz = (Class<T>) obj.getClass();
		final T t = (T) obj;
		return isSerializabeObject(t, clazz);
	}

	private static boolean isSerializabeObjectTryCatch(Object obj) {
		try(final var out = new OutputStream() {

			@Override
			public void write(int b) {
			}

		};
		final var objput = new ObjectOutputStream(out)) {

			objput.writeObject(obj);

		}catch(Throwable e) {
			return false;
		}
		return true;
	}


}