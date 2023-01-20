package com.greentree.action;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.Test;

import com.greentree.action.container.MultiContainer;

public class MultiContainerSerializableTest {

	@Test
	void test1() throws ClassNotFoundException, IOException {
		final var c1 = new MultiContainer<Runnable>();
		c1.add(() -> assertTrue(false));
		assertFalse(c1.isEmpty());
		final var c2 = (MultiContainer<?>)deser(ser(c1));
		assertTrue(c2.isEmpty());
	}
	
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
}
