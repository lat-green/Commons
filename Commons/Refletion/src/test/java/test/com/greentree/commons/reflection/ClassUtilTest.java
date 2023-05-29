package test.com.greentree.commons.reflection;

import static com.greentree.commons.reflection.ClassUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.reflection.ClassUtil;

/**
 * testing ClassUtil.write and ClassUtil.read
 * @author User
 */
public class ClassUtilTest {

	static Stream<Object> arrays() {
		return Stream.of(new int[0], new int[1]);
	}
	static Stream<Object> mutable() {
		return Stream.of(new int[1]);
	}
	static Stream<Object> objs() {
		return Stream.of("5", 5, new A(), null, new ArrayList<>());
	}

	static Stream<Object> testIO() {
		ArrayList<String> strs = new ArrayList<>();
		strs.add("a");
		strs.add("b");
		strs.add("c");
		return Stream.of("test_string", 5, strs);
	}

	static Stream<Object> unmutable() {
		return Stream.of("5", 5, new int[0], new constClass(1), new A());
	}

	@ParameterizedTest
	@MethodSource("objs")
	void CloneEquals(Object obj) {
		final var clone = ClassUtil.clone(obj);
		assertEquals(obj, clone);
	}

	@ParameterizedTest
	@MethodSource("unmutable")
	void isMutableFalse(Object obj) {
		assertFalse(isMutable(obj), obj + " is not unmutable");
	}

	@ParameterizedTest
	@MethodSource("mutable")
	void isMutableTrue(Object obj) {
		assertTrue(isMutable(obj), obj + " is not mutable");
	}

	@ParameterizedTest
	@MethodSource
	void testIO(Object a) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		File file = Files.createTempFile("temp", "").toFile();

		try(FileOutputStream fout = new FileOutputStream(file);
				ObjectOutput out = new ObjectOutputStream(fout);) {
			write(a, out);
		}

		Object b = null;

		try(FileInputStream fin = new FileInputStream(file);
				ObjectInput in = new ObjectInputStream(fin);) {
			b = read(in, a.getClass());
		}

		assertEquals(a, b);
	}

	static class A {
		final B b;

		public A() {
			b = null;
		}
	}

	static class B {
		final A a;

		public B() {
			a = null;
		}
	}

	static class constClass {

		final constClass cc;
		final int x;

		public constClass(int x) {
			this.x = x;
			cc = null;
		}
	}

}
