package com.greentree.common.web._private;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class ByteUtils {


	public static int bytesToInteger(byte[] bytes) {
		return bytesToInteger(bytes, 0);
	}
	public static int bytesToInteger(byte[] bytes, int off) {
		final ByteBuffer INT = ByteBuffer.allocate(Integer.BYTES);
		INT.put(bytes, off, Integer.BYTES);
		INT.position(0);
		return INT.getInt();
	}

	@SuppressWarnings("unchecked")
	public static <T> T bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		try(final var bin = new ByteArrayInputStream(bytes);
				final var oin = new ObjectInputStream(bin);
				) {
			return (T) oin.readObject();
		}
	}
	public static byte[] intToBytes(int x) {
		final var buf = new byte[Integer.BYTES];
		intToBytes(x, buf, 0);
		return buf;
	}

	public static void intToBytes(int x, byte[] buf, int off) {
		final ByteBuffer INT = ByteBuffer.allocate(Integer.BYTES);
		INT.putInt(0, x);
		INT.position(0);
		final var arr = INT.array();
		for(int i = 0; i < Integer.BYTES; i++) 
			buf[i + off] = arr[i];

	}
	
	public static byte[] merge(byte[] a, byte[] b) {
		final var c = new byte[a.length + b.length];
		for(int i = 0; i < a.length; i++) c[i] = a[i];
		for(int i = 0; i < b.length; i++) c[i + a.length] = b[i];
		return c;
	}
	public static byte[] objToBytes(Object obj) throws IOException {
		try(final var bout = new ByteArrayOutputStream();
				final var oout = new ObjectOutputStream(bout);
				) {
			oout.writeObject(obj);
			return bout.toByteArray();
		}
	}
}
