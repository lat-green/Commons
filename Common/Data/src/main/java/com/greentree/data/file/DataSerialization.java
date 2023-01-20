package com.greentree.data.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataSerialization {

	public static byte[] ser(Object obj) throws IOException {
		if(obj == null) return new byte[] {};
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var out = new ObjectOutputStream(bout)) {
				out.writeObject(obj);
			}
			return bout.toByteArray();
		}
	}
	
	public static Object deser(byte[] obj) throws IOException, ClassNotFoundException {
		if(obj == null || obj.length == 0) return null;
		try(final var bin = new ByteArrayInputStream(obj)) {
			try(final var in = new ObjectInputStream(bin)) {
				return in.readObject();
			}
		}
	}

	public static byte[] zeroDereduce(byte[] fs) throws IOException {
		try(final var bout = new ByteArrayOutputStream();) {
			try(final var out = new DataOutputStream(bout);
				final var bin = new ByteArrayInputStream(fs);
				final var in = new DataInputStream(bin);) {
					while(in.available() > 0) {
						final var b = in.readByte();
						if(b == 0) {
							var c = in.read();
							while(c-- > 0)
								out.writeByte(0);
						}else
							out.writeByte(b);
					}
			}
			return bout.toByteArray();
		}
	}
	
	private static final int ZERO_BLOCK_SIZE = Byte.MAX_VALUE - Byte.MIN_VALUE;
	
	public static byte[] zeroReduce(byte[] fs) throws IOException {
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var out = new DataOutputStream(bout)) {
        		int zCount = 0;
        		for(int i = 0; i < fs.length; i++) {
        			if(fs[i] == 0) {
        				zCount++;
        			} else {
        				while(zCount > 0) {
        					final var s = Math.min(zCount, ZERO_BLOCK_SIZE);
            				out.writeByte(0);
            				out.write(s);
            				zCount -= s;
            			}
        				out.writeByte(fs[i]);
        			}
        		}
				while(zCount > 0) {
					final var s = Math.min(zCount, ZERO_BLOCK_SIZE);
    				out.writeByte(0);
    				out.write(s);
    				zCount -= s;
    			}
			}
			final var res = bout.toByteArray();
			return res;
		}
	}
	
}
