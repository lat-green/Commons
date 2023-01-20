package com.greentree.commons.data.compress;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.greentree.commons.util.iterator.IteratorUtil;

public interface ByteCompressor {

	byte[] compress(InputStream in) throws IOException;

	default byte[] compress(byte[] buf) throws IOException {
		return compress(buf, 0, buf.length);
	}
	default byte[] uncompress(byte[] buf) throws IOException {
		return uncompress(buf, 0, buf.length);
	}
	
	byte[] uncompress(InputStream in) throws IOException;

	default byte[] compress(byte[] buf, int offset, int length) throws IOException {
		try(final var in = new ByteArrayInputStream(buf, offset, length)) {
			return compress(in);
		}
	}
	default byte[] uncompress(byte[] buf, int offset, int length) throws IOException {
		try(final var in = new ByteArrayInputStream(buf, offset, length)) {
			return uncompress(in);
		}
	}

	static ByteCompressor union(ByteCompressor a, ByteCompressor b) {
		return new UnionByteCompressor(a, b);
	}
	
	static ByteCompressor union(ByteCompressor...cs) {
		return IteratorUtil.reduce(IteratorUtil.iterable(cs), (a, b) -> union(a, b));
	}

}
