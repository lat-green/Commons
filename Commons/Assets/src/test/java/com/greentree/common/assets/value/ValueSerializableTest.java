package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.NullValue;

public class ValueSerializableTest {
	
	@SuppressWarnings("unchecked")
	private <T> T deser(byte[] ser) throws IOException, ClassNotFoundException {
		try(final var bout = new ByteArrayInputStream(ser)) {
			try(final var oout = new ObjectInputStream(bout)) {
				return (T) oout.readObject();
			}
		}
	}
	
	private byte[] ser(Object obj) throws IOException {
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var oout = new ObjectOutputStream(bout)) {
				oout.writeObject(obj);
			}
			return bout.toByteArray();
		}
	}
	
	@Test
	void MutableValue_after_Serialization() throws ClassNotFoundException, IOException {
		final var str = "Hello World";
		final var strv = new MutableValue<>(str);
		@SuppressWarnings("unchecked")
		final var c = (MutableValue<String>) deser(ser(strv));
		assertEquals(strv.get(), c.get());
	}
	
	@Test
	void NullValue_Serialization() throws ClassNotFoundException, IOException {
		final var v1 = NullValue.instance();
		final var v2 = deser(ser(v1));
		assertEquals(v1, v2);
		assertTrue(v1 == v2);
	}
	
}
