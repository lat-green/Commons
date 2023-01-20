package com.greentree.commons.image.loader;

import static com.greentree.commons.image.PixelFormat.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.greentree.commons.image.PixelFormat;

public class PNGDecoder {

	private static final byte[] SIGNATURE = new byte[]{-119,80,78,71,13,10,26,10};

	private int bitdepth;
	private final byte[] buffer;
	private int bytesPerPixel;
	private int chunkLength;
	private int chunkRemaining;
	private int chunkType;
	private int colorType;
	private final CRC32 crc;
	private int height;
	private final InputStream input;
	private byte[] palette;
	private byte[] paletteA;
	private byte[] transPixel;
	private int width;

	public PNGDecoder(final InputStream input) throws IOException {
		this.input = input;
		crc = new CRC32();
		readFully(buffer = new byte[4096], 0, PNGDecoder.SIGNATURE.length);
		if(!PNGDecoder.checkSignature(buffer)) throw new IOException("Not a valid PNG file");
		this.openChunk(1229472850);
		readIHDR();
		closeChunk();
		Label_0120 : while(true) {
			this.openChunk();
			switch(chunkType) {
				case 1229209940: {
					break Label_0120;
				}
				case 1347179589: {
					readPLTE();
					break;
				}
				case 1951551059: {
					readtRNS();
					break;
				}
			}
			closeChunk();
		}
		if(colorType == 3 && palette == null) throw new IOException("Missing PLTE chunk");
	}

	private static boolean checkSignature(final byte[] buffer) {
		for(int i = 0; i < PNGDecoder.SIGNATURE.length; ++i) if(buffer[i] != PNGDecoder.SIGNATURE[i]) return false;
		return true;
	}

	public PixelFormat decideTextureFormat(final PixelFormat fmt) {
		switch(colorType) {
			case 2: {
				if(fmt == ABGR || fmt == RGBA || fmt == BGRA || fmt == RGB)
					return fmt;
				return RGB;
			}
			case 6: {
				if(fmt == ABGR || fmt == RGBA || fmt == BGRA || fmt == RGB)
					return fmt;
				return RGBA;
			}
			case 0: {
				if(fmt == LUMINANCE || fmt == ALPHA) return fmt;
				return LUMINANCE;
			}
			case 4: {
				return LUMINANCE_ALPHA;
			}
			case 3: {
				if(fmt == ABGR || fmt == RGBA || fmt == BGRA) return fmt;
				return RGBA;
			}
			default: {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		}
	}

	public void decode(final ByteBuffer buffer, final int stride, final PixelFormat fmt) throws IOException {
		final int offset = buffer.position();
		final int lineSize = (width * bitdepth + 7) / 8 * bytesPerPixel;
		byte[] curLine = new byte[lineSize + 1];
		byte[] prevLine = new byte[lineSize + 1];
		byte[] palLine = bitdepth < 8 ? new byte[width + 1] : null;
		final Inflater inflater = new Inflater();
		try {
			for(int y = 0; y < height; ++y) {
				readChunkUnzip(inflater, curLine, 0, curLine.length);
				unfilter(curLine, prevLine);
				buffer.position(offset + y * stride);
				switch(colorType) {
					case 2: {
						if(fmt == ABGR) {
							copyRGBtoABGR(buffer, curLine);
							break;
						}
						if(fmt == RGBA) {
							copyRGBtoRGBA(buffer, curLine);
							break;
						}
						if(fmt == BGRA) {
							copyRGBtoBGRA(buffer, curLine);
							break;
						}
						if(fmt == RGB) {
							copy(buffer, curLine);
							break;
						}
						throw new UnsupportedOperationException("Unsupported format for this image");
					}
					case 6: {
						if(fmt == ABGR) {
							copyRGBAtoABGR(buffer, curLine);
							break;
						}
						if(fmt == RGBA) {
							copy(buffer, curLine);
							break;
						}
						if(fmt == BGRA) {
							copyRGBAtoBGRA(buffer, curLine);
							break;
						}
						if(fmt == RGB) {
							copyRGBAtoRGB(buffer, curLine);
							break;
						}
						throw new UnsupportedOperationException("Unsupported format for this image");
					}
					case 0: {
						if(fmt == LUMINANCE || fmt == ALPHA) {
							copy(buffer, curLine);
							break;
						}
						throw new UnsupportedOperationException("Unsupported format for this image");
					}
					case 4: {
						if(fmt == LUMINANCE_ALPHA) {
							copy(buffer, curLine);
							break;
						}
						throw new UnsupportedOperationException("Unsupported format for this image");
					}
					case 3: {
						switch(bitdepth) {
							case 8: {
								palLine = curLine;
								break;
							}
							case 4: {
								expand4(curLine, palLine);
								break;
							}
							case 2: {
								expand2(curLine, palLine);
								break;
							}
							case 1: {
								expand1(curLine, palLine);
								break;
							}
							default: {
								throw new UnsupportedOperationException("Unsupported bitdepth for this image");
							}
						}
						if(fmt == ABGR) {
							copyPALtoABGR(buffer, palLine);
							break;
						}
						if(fmt == RGBA) {
							copyPALtoRGBA(buffer, palLine);
							break;
						}
						if(fmt == BGRA) {
							copyPALtoBGRA(buffer, palLine);
							break;
						}
						throw new UnsupportedOperationException("Unsupported format for this image");
					}
					default: {
						throw new UnsupportedOperationException("Not yet implemented");
					}
				}
				final byte[] tmp = curLine;
				curLine = prevLine;
				prevLine = tmp;
			}
		}finally {
			inflater.end();
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean hasAlpha() {
		return colorType == 6 || paletteA != null || transPixel != null;
	}

	public boolean isRGB() {
		return colorType == 6 || colorType == 2 || colorType == 3;
	}

	private void checkChunkLength(final int expected) throws IOException {
		if(chunkLength != expected) throw new IOException("Chunk has wrong size");
	}

	private void closeChunk() throws IOException {
		if(chunkRemaining > 0) skip(chunkRemaining + 4);
		else {
			readFully(buffer, 0, 4);
			final int expectedCrc = readInt(buffer, 0);
			final int computedCrc = (int) crc.getValue();
			if(computedCrc != expectedCrc) throw new IOException("Invalid CRC");
		}
		chunkRemaining = 0;
		chunkLength = 0;
		chunkType = 0;
	}

	private void copy(final ByteBuffer buffer, final byte[] curLine) {
		buffer.put(curLine, 1, curLine.length - 1);
	}

	private void copyPALtoABGR(final ByteBuffer buffer, final byte[] curLine) {
		if(paletteA != null) for(int i = 1, n = curLine.length; i < n; ++i) {
			final int idx = curLine[i] & 0xFF;
			final byte r = palette[idx * 3 + 0];
			final byte g = palette[idx * 3 + 1];
			final byte b = palette[idx * 3 + 2];
			final byte a = paletteA[idx];
			buffer.put(a).put(b).put(g).put(r);
		}
		else for(int i = 1, n = curLine.length; i < n; ++i) {
			final int idx = curLine[i] & 0xFF;
			final byte r = palette[idx * 3 + 0];
			final byte g = palette[idx * 3 + 1];
			final byte b = palette[idx * 3 + 2];
			final byte a = -1;
			buffer.put(a).put(b).put(g).put(r);
		}
	}

	private void copyPALtoBGRA(final ByteBuffer buffer, final byte[] curLine) {
		if(paletteA != null) for(int i = 1, n = curLine.length; i < n; ++i) {
			final int idx = curLine[i] & 0xFF;
			final byte r = palette[idx * 3 + 0];
			final byte g = palette[idx * 3 + 1];
			final byte b = palette[idx * 3 + 2];
			final byte a = paletteA[idx];
			buffer.put(b).put(g).put(r).put(a);
		}
		else for(int i = 1, n = curLine.length; i < n; ++i) {
			final int idx = curLine[i] & 0xFF;
			final byte r = palette[idx * 3 + 0];
			final byte g = palette[idx * 3 + 1];
			final byte b = palette[idx * 3 + 2];
			final byte a = -1;
			buffer.put(b).put(g).put(r).put(a);
		}
	}

	private void copyPALtoRGBA(final ByteBuffer buffer, final byte[] curLine) {
		if(paletteA != null) for(int i = 1, n = curLine.length; i < n; ++i) {
			final int idx = curLine[i] & 0xFF;
			final byte r = palette[idx * 3 + 0];
			final byte g = palette[idx * 3 + 1];
			final byte b = palette[idx * 3 + 2];
			final byte a = paletteA[idx];
			buffer.put(r).put(g).put(b).put(a);
		}
		else for(int i = 1, n = curLine.length; i < n; ++i) {
			final int idx = curLine[i] & 0xFF;
			final byte r = palette[idx * 3 + 0];
			final byte g = palette[idx * 3 + 1];
			final byte b = palette[idx * 3 + 2];
			final byte a = -1;
			buffer.put(r).put(g).put(b).put(a);
		}
	}

	private void copyRGBAtoABGR(final ByteBuffer buffer, final byte[] curLine) {
		for(int i = 1, n = curLine.length; i < n; i += 4)
			buffer.put(curLine[i + 3]).put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i]);
	}

	private void copyRGBAtoBGRA(final ByteBuffer buffer, final byte[] curLine) {
		for(int i = 1, n = curLine.length; i < n; i += 4)
			buffer.put(curLine[i + 2]).put(curLine[i + 1]).put(curLine[i + 0]).put(curLine[i + 3]);
	}

	private void copyRGBAtoRGB(final ByteBuffer buffer, final byte[] curLine) {
		for(int i = 1, n = curLine.length; i < n; i += 4)
			buffer.put(curLine[i]).put(curLine[i + 1]).put(curLine[i + 2]);
	}

	private void copyRGBtoABGR(final ByteBuffer buffer, final byte[] curLine) {
		if(transPixel != null) {
			final byte tr = transPixel[1];
			final byte tg = transPixel[3];
			final byte tb = transPixel[5];
			for(int i = 1, n = curLine.length; i < n; i += 3) {
				final byte r = curLine[i];
				final byte g = curLine[i + 1];
				final byte b = curLine[i + 2];
				byte a = -1;
				if(r == tr && g == tg && b == tb) a = 0;
				buffer.put(a).put(b).put(g).put(r);
			}
		}else for(int j = 1, n2 = curLine.length; j < n2; j += 3)
			buffer.put((byte) -1).put(curLine[j + 2]).put(curLine[j + 1]).put(curLine[j]);
	}

	private void copyRGBtoBGRA(final ByteBuffer buffer, final byte[] curLine) {
		if(transPixel != null) {
			final byte tr = transPixel[1];
			final byte tg = transPixel[3];
			final byte tb = transPixel[5];
			for(int i = 1, n = curLine.length; i < n; i += 3) {
				final byte r = curLine[i];
				final byte g = curLine[i + 1];
				final byte b = curLine[i + 2];
				byte a = -1;
				if(r == tr && g == tg && b == tb) a = 0;
				buffer.put(b).put(g).put(r).put(a);
			}
		}else for(int j = 1, n2 = curLine.length; j < n2; j += 3)
			buffer.put(curLine[j + 2]).put(curLine[j + 1]).put(curLine[j]).put((byte) -1);
	}

	private void copyRGBtoRGBA(final ByteBuffer buffer, final byte[] curLine) {
		if(transPixel != null) {
			final byte tr = transPixel[1];
			final byte tg = transPixel[3];
			final byte tb = transPixel[5];
			for(int i = 1, n = curLine.length; i < n; i += 3) {
				final byte r = curLine[i];
				final byte g = curLine[i + 1];
				final byte b = curLine[i + 2];
				byte a = -1;
				if(r == tr && g == tg && b == tb) a = 0;
				buffer.put(r).put(g).put(b).put(a);
			}
		}else for(int j = 1, n2 = curLine.length; j < n2; j += 3)
			buffer.put(curLine[j]).put(curLine[j + 1]).put(curLine[j + 2]).put((byte) -1);
	}

	private void expand1(final byte[] src, final byte[] dst) {
		int i = 1;
		final int n = dst.length;
		while(i < n) {
			final int val = src[1 + (i >> 3)] & 0xFF;
			switch(n - i) {
				default: {
					dst[i + 7] = (byte) (val & 0x1);
				}
				case 7: {
					dst[i + 6] = (byte) (val >> 1 & 0x1);
				}
				case 6: {
					dst[i + 5] = (byte) (val >> 2 & 0x1);
				}
				case 5: {
					dst[i + 4] = (byte) (val >> 3 & 0x1);
				}
				case 4: {
					dst[i + 3] = (byte) (val >> 4 & 0x1);
				}
				case 3: {
					dst[i + 2] = (byte) (val >> 5 & 0x1);
				}
				case 2: {
					dst[i + 1] = (byte) (val >> 6 & 0x1);
				}
				case 1: {
					dst[i] = (byte) (val >> 7);
					i += 8;
				}
			}
		}
	}

	private void expand2(final byte[] src, final byte[] dst) {
		int i = 1;
		final int n = dst.length;
		while(i < n) {
			final int val = src[1 + (i >> 2)] & 0xFF;
			switch(n - i) {
				default: {
					dst[i + 3] = (byte) (val & 0x3);
				}
				case 3: {
					dst[i + 2] = (byte) (val >> 2 & 0x3);
				}
				case 2: {
					dst[i + 1] = (byte) (val >> 4 & 0x3);
				}
				case 1: {
					dst[i] = (byte) (val >> 6);
					i += 4;
				}
			}
		}
	}

	private void expand4(final byte[] src, final byte[] dst) {
		int i = 1;
		final int n = dst.length;
		while(i < n) {
			final int val = src[1 + (i >> 1)] & 0xFF;
			switch(n - i) {
				default: {
					dst[i + 1] = (byte) (val & 0xF);
				}
				case 1: {
					dst[i] = (byte) (val >> 4);
					i += 2;
				}
			}
		}
	}

	private void openChunk() throws IOException {
		readFully(buffer, 0, 8);
		chunkLength = readInt(buffer, 0);
		chunkType = readInt(buffer, 4);
		chunkRemaining = chunkLength;
		crc.reset();
		crc.update(buffer, 4, 4);
	}

	private void openChunk(final int expected) throws IOException {
		this.openChunk();
		if(chunkType != expected) throw new IOException("Expected chunk: " + Integer.toHexString(expected));
	}

	private int readChunk(final byte[] buffer, final int offset, int length) throws IOException {
		if(length > chunkRemaining) length = chunkRemaining;
		readFully(buffer, offset, length);
		crc.update(buffer, offset, length);
		chunkRemaining -= length;
		return length;
	}

	private void readChunkUnzip(final Inflater inflater, final byte[] buffer, int offset, int length)
			throws IOException {
		try {
			do {
				final int read = inflater.inflate(buffer, offset, length);
				if(read <= 0) {
					if(inflater.finished()) throw new EOFException();
					if(!inflater.needsInput()) throw new IOException("Can't inflate " + length + " bytes");
					refillInflater(inflater);
				}else {
					offset += read;
					length -= read;
				}
			}while(length > 0);
		}catch(final DataFormatException ex) {
			throw (IOException) new IOException("inflate error").initCause(ex);
		}
	}

	private void readFully(final byte[] buffer, int offset, int length) throws IOException {
		do {
			final int read = input.read(buffer, offset, length);
			if(read < 0) throw new EOFException();
			offset += read;
			length -= read;
		}while(length > 0);
	}

	private void readIHDR() throws IOException {
		checkChunkLength(13);
		readChunk(buffer, 0, 13);
		width = readInt(buffer, 0);
		height = readInt(buffer, 4);
		bitdepth = buffer[8] & 0xFF;
		Label_0428 : {
			switch(colorType = buffer[9] & 0xFF) {
				case 0: {
					if(bitdepth != 8) throw new IOException("Unsupported bit depth: " + bitdepth);
					bytesPerPixel = 1;
					break;
				}
				case 4: {
					if(bitdepth != 8) throw new IOException("Unsupported bit depth: " + bitdepth);
					bytesPerPixel = 2;
					break;
				}
				case 2: {
					if(bitdepth != 8) throw new IOException("Unsupported bit depth: " + bitdepth);
					bytesPerPixel = 3;
					break;
				}
				case 6: {
					if(bitdepth != 8) throw new IOException("Unsupported bit depth: " + bitdepth);
					bytesPerPixel = 4;
					break;
				}
				case 3: {
					switch(bitdepth) {
						case 1:
						case 2:
						case 4:
						case 8: {
							bytesPerPixel = 1;
							break Label_0428;
						}
						default: {
							throw new IOException("Unsupported bit depth: " + bitdepth);
						}
					}
				}
				default: {
					throw new IOException("unsupported color format: " + colorType);
				}
			}
		}
		if(buffer[10] != 0) throw new IOException("unsupported compression method");
		if(buffer[11] != 0) throw new IOException("unsupported filtering method");
		if(buffer[12] != 0) throw new IOException("unsupported interlace method");
	}

	private int readInt(final byte[] buffer, final int offset) {
		return buffer[offset] << 24 | (buffer[offset + 1] & 0xFF) << 16 | (buffer[offset + 2] & 0xFF) << 8
				| buffer[offset + 3] & 0xFF;
	}

	private void readPLTE() throws IOException {
		final int paletteEntries = chunkLength / 3;
		if(paletteEntries < 1 || paletteEntries > 256 || chunkLength % 3 != 0)
			throw new IOException("PLTE chunk has wrong length");
		readChunk(palette = new byte[paletteEntries * 3], 0, palette.length);
	}

	private void readtRNS() throws IOException {
		switch(colorType) {
			case 0: {
				checkChunkLength(2);
				readChunk(transPixel = new byte[2], 0, 2);
				break;
			}
			case 2: {
				checkChunkLength(6);
				readChunk(transPixel = new byte[6], 0, 6);
				break;
			}
			case 3: {
				if(palette == null) throw new IOException("tRNS chunk without PLTE chunk");
				Arrays.fill(paletteA = new byte[palette.length / 3], (byte) -1);
				readChunk(paletteA, 0, paletteA.length);
				break;
			}
		}
	}

	private void refillInflater(final Inflater inflater) throws IOException {
		while(chunkRemaining == 0) {
			closeChunk();
			this.openChunk(1229209940);
		}
		final int read = readChunk(buffer, 0, buffer.length);
		inflater.setInput(buffer, 0, read);
	}

	private void skip(long amount) throws IOException {
		while(amount > 0L) {
			final long skipped = input.skip(amount);
			if(skipped < 0L) throw new EOFException();
			amount -= skipped;
		}
	}

	private void unfilter(final byte[] curLine, final byte[] prevLine) throws IOException {
		switch(curLine[0]) {
			case 0: {
				break;
			}
			case 1: {
				unfilterSub(curLine);
				break;
			}
			case 2: {
				unfilterUp(curLine, prevLine);
				break;
			}
			case 3: {
				unfilterAverage(curLine, prevLine);
				break;
			}
			case 4: {
				unfilterPaeth(curLine, prevLine);
				break;
			}
			default: {
				throw new IOException("invalide filter type in scanline: " + curLine[0]);
			}
		}
	}

	private void unfilterAverage(final byte[] curLine, final byte[] prevLine) {
		int bpp;
		int i;
		for(bpp = bytesPerPixel, i = 1; i <= bpp; ++i) {
			final int n2 = i;
			curLine[n2] += (byte) ((prevLine[i] & 0xFF) >>> 1);
		}
		for(final int n = curLine.length; i < n; ++i) {
			final int n3 = i;
			curLine[n3] += (byte) ((prevLine[i] & 0xFF) + (curLine[i - bpp] & 0xFF) >>> 1);
		}
	}

	private void unfilterPaeth(final byte[] curLine, final byte[] prevLine) {
		int bpp;
		int i;
		for(bpp = bytesPerPixel, i = 1; i <= bpp; ++i) {
			final int n2 = i;
			curLine[n2] += prevLine[i];
		}
		for(final int n = curLine.length; i < n; ++i) {
			final int a = curLine[i - bpp] & 0xFF;
			final int b = prevLine[i] & 0xFF;
			int c = prevLine[i - bpp] & 0xFF;
			final int p = a + b - c;
			int pa = p - a;
			if(pa < 0) pa = -pa;
			int pb = p - b;
			if(pb < 0) pb = -pb;
			int pc = p - c;
			if(pc < 0) pc = -pc;
			if(pa <= pb && pa <= pc) c = a;
			else if(pb <= pc) c = b;
			final int n3 = i;
			curLine[n3] += (byte) c;
		}
	}

	private void unfilterSub(final byte[] curLine) {
		final int bpp = bytesPerPixel;
		for(int i = bpp + 1, n = curLine.length; i < n; ++i) {
			final int n2 = i;
			curLine[n2] += curLine[i - bpp];
		}
	}

	private void unfilterUp(final byte[] curLine, final byte[] prevLine) {
		for(int i = 1, n = curLine.length; i < n; ++i) {
			final int n2 = i;
			curLine[n2] += prevLine[i];
		}
	}

}
