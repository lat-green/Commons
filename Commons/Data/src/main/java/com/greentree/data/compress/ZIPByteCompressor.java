package com.greentree.data.compress;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import com.greentree.common.util.exception.WrappedException;

public class ZIPByteCompressor implements ByteCompressor {

	@Override
	public byte[] compress(InputStream in) throws IOException {
		final var input  = in.readAllBytes();
		final var output = new byte[input.length];

		Deflater compresser = new Deflater();
		compresser.setInput(input);
		compresser.finish();
		int compressedDataLength = compresser.deflate(output);
		compresser.end();

		final var result = new byte[compressedDataLength];
		System.arraycopy(output, 0, result, 0, compressedDataLength);
		return result;
	}

	@Override
	public byte[] uncompress(InputStream in) throws IOException {
		try {
    		final var input  = in.readAllBytes();
    		final var output = new byte[input.length];
    
    		Inflater decompresser = new Inflater();
    		decompresser.setInput(input);
    		int resultLength = decompresser.inflate(output);
    		decompresser.end();
    
    		final var result = new byte[resultLength];
    		System.arraycopy(output, 0, result, 0, resultLength);
    		return result;
		}catch(DataFormatException e) {
			throw new WrappedException(e);
		}
	}

}
