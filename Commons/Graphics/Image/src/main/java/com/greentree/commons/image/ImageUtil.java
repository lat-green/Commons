package com.greentree.commons.image;


public class ImageUtil {

	public static float[] to_floats(byte[] bs) {
		final var fs = new float[bs.length];
		for(int i = 0; i < bs.length; i++)
			fs[i] = to_float(bs[i]);
		return fs;
	}
	
	public static float to_float(byte b) {
		float d = Byte.toUnsignedInt(b);
		d += 0.5;
		d /= 255;
		return d;
	}

	public static byte[] to_bytes(float[] fs) {
		final var bs = new byte[fs.length];
		for(int i = 0; i < bs.length; i++)
			bs[i] = to_byte(fs[i]);
		return bs;
	}

	public static byte to_byte(float f) {
		f *= 255f;
//		f += 0.5f;
		return (byte) f;
	}
	
}
