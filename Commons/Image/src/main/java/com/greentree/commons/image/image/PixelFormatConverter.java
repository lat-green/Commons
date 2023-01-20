package com.greentree.commons.image.image;

import com.greentree.commons.image.PixelFormat;
import com.greentree.commons.util.collection.Table;

public class PixelFormatConverter {

	private static final Table<PixelFormat, PixelFormat, FormatConverter> table = new Table<>();
	static {
		table.put(PixelFormat.RGB, PixelFormat.RGBA, PixelFormatConverter::convertRGBtoRGBA);
		table.put(PixelFormat.RGBA, PixelFormat.RGB, PixelFormatConverter::convertRGBAtoRGB);

		for(var pf : PixelFormat.values())
			table.put(pf, pf, (arr, s) -> {
				return subArray(arr, s, pf.numComponents);
			});
	}

	public static byte[] convert(byte[] byes, PixelFormat in, PixelFormat out) {
		return convert(byes, 0, in, out);
	}
	
	private static byte[] subArray(byte[] arr, int s, int numComponents) {
		final var res = new byte[numComponents];
		for(int i = 0; i < numComponents; i++)
			res[i] = arr[s + i];
		return res;
	}

	public static byte[] convert(byte[] byes, int start, PixelFormat in, PixelFormat out) {
		return table.get(in, out).convert(byes, start);
	}

	private static byte[] convertRGBAtoRGB(final byte[] in, int start) {
		PixelFormatChecker.check(PixelFormat.RGBA, in);
		return new byte[] {
				in[0], in[1], in[2]
		};
	}

	private static byte[] convertRGBtoRGBA(final byte[] in, int start) {
		PixelFormatChecker.check(PixelFormat.RGB, in);
		return new byte[] {
				in[0], in[1], in[2], -128
		};
	}

	@FunctionalInterface
	private interface FormatConverter {

		byte[] convert(byte[] in, int start);

	}

}
