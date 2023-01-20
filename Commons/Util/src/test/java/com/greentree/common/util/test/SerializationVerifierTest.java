package com.greentree.common.util.test;

import static com.greentree.commons.util.classes.SerializationVerifier.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.greentree.commons.util.cortege.Pair;

/**
 * testing ClassUtil.write and ClassUtil.read
 * @author User
 */
public class SerializationVerifierTest {

	private final static String SOME_STRING = "Hello world";

	@Test
	void testIsSerializabeClassFalse() {
		assertFalse(isSerializabeClass(NotSerializableClass.class));
		assertFalse(isSerializabeClass(InputStream.class));
		assertFalse(isSerializabeClass(Collection.class));
		assertFalse(isSerializabeClass(Object.class));
		assertFalse(isSerializabeClass(Object[].class));
	}
	@Test
	void testIsSerializabeClassTrue() {
		assertTrue(isSerializabeClass(String.class));
		assertTrue(isSerializabeClass(String[].class));
		assertTrue(isSerializabeClass(ArrayList.class));
		assertTrue(isSerializabeClass(Integer.class));
		assertTrue(isSerializabeClass(int.class));
		assertTrue(isSerializabeClass(int[].class));
		assertTrue(isSerializabeClass(SerializableClass.class));
	}
	@Test
	void testIsSerializabeObjectFalse() {
		assertFalse(isSerializabeObject(new Object()));
		assertFalse(isSerializabeObject(new Object[0]));
		assertFalse(isSerializabeObject(new Object[1]));
		assertFalse(isSerializabeObject(new NotSerializableClass()));
	}



	@Test
	void testIsSerializabeObjectTrue() {
		assertTrue(isSerializabeObject(5));
		assertTrue(isSerializabeObject(Object.class));
		assertTrue(isSerializabeObject(new int[0]));
		assertTrue(isSerializabeObject(new int[1]));
		assertTrue(isSerializabeObject(new String[0]));
		assertTrue(isSerializabeObject(new String[1]));
		assertTrue(isSerializabeObject(SOME_STRING));
		assertTrue(isSerializabeObject(new SerializableClass()));
		assertTrue(isSerializabeObject(new Pair<>(SOME_STRING, SOME_STRING)));
		assertTrue(isSerializabeObject(new A()));
		assertTrue(isSerializabeObject(new B()));
	}

	static class A extends SerializableClass {
		private static final long serialVersionUID = 1L;
		B b;
	}

	static class B extends SerializableClass {
		private static final long serialVersionUID = 1L;
		A b;
	}

	static class NotSerializableClass {

	}

	static class SerializableClass implements Serializable {
		private static final long serialVersionUID = 1L;

	}

}
