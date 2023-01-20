package com.greentree.common.image.image;

import java.util.HashMap;
import java.util.Map;

import com.greentree.common.image.PixelFormat;

public class PixelFormatChecker {

	private static final Map<PixelFormat, FormatCheker> table = new HashMap<>();
	static {
	}

	public static boolean check(PixelFormat format, byte[] bytes) {
		var checker = table.get(format);
		if(checker == null) {
			checker = genCheckerDefault(format);
			table.put(format, checker);
		}
		return checker.check(bytes);
	}

	private static FormatCheker genCheckerDefault(PixelFormat format) {
		return bytes -> bytes.length == format.numComponents;
	}

	@FunctionalInterface
	private interface FormatCheker {

		boolean check(byte[] bytes);

	}

}
