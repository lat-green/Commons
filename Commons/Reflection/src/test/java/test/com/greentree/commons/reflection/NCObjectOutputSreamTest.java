package test.com.greentree.commons.reflection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.reflection.NCObjectOutputSream;

public class NCObjectOutputSreamTest {

	public static byte[] ser(Object obj) throws IOException {
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var ncout = new NCObjectOutputSream(bout)) {
				ncout.writeObject(obj);
			}
			return bout.toByteArray();
		}
	}

	static Stream<Object> objs() {
		return Stream.of("A", new ArrayList<>());
	}

	@MethodSource("objs")
	@ParameterizedTest
	void test1(Object obj) throws IOException {
		final var ser = ser(obj);
//		System.out.println(new String(ser));
	}

}
