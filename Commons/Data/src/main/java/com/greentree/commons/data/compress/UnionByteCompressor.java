package com.greentree.commons.data.compress;

import java.io.IOException;
import java.io.InputStream;

public record UnionByteCompressor(ByteCompressor first, ByteCompressor seconde) implements ByteCompressor {

	@Override
	public byte[] compress(InputStream in) throws IOException {
		return seconde.compress(first.compress(in));
	}
	
	@Override
	public byte[] uncompress(InputStream in) throws IOException {
		return first.uncompress(seconde.uncompress(in));
	}
	
}
