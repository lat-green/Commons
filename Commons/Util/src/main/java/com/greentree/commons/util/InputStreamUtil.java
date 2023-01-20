package com.greentree.commons.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class InputStreamUtil {

	public static void copy(InputStream source, OutputStream target) throws IOException {
		Objects.requireNonNull(source);
		Objects.requireNonNull(target);
		try(source;
				final var buf = new BufferedOutputStream(target)) {
			buf.write(source.readAllBytes());
		}
	}

	public static String readString(InputStream input, Charset charset) {
		try(Reader reader = new BufferedReader(new InputStreamReader(input, charset))) {
			final StringBuilder result = new StringBuilder();
			final char[]        buff   = new char[512]; // 1kb
			for(int read = reader.read(buff); read > 0; read = reader.read(buff)) result.append(buff, 0, read);
			return result.toString();
		}catch(final IOException exc) {
			throw new IllegalStateException("Can not load source", exc);
		}
	}
	public static String readString(InputStream input) {
		return readString(input, StandardCharsets.UTF_8);
	}

}
