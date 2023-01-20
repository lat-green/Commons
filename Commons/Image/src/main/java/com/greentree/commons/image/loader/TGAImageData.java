package com.greentree.commons.image.loader;


import static com.greentree.commons.image.PixelFormat.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.greentree.commons.image.image.ImageData;

public class TGAImageData implements ImageDataLoader {

	public final static TGAImageData IMAGE_DATA_LOADER = new TGAImageData();

	private TGAImageData() {
	}

	@Override
	public ImageData loadImage(final InputStream fis) throws IOException {
		final int width, height;
		final int texWidth, texHeight;
		final short pixelDepth;

		var flipped = false;

		byte red = 0;
		byte green = 0;
		byte blue = 0;
		byte alpha = 0;
		final var bis = new BufferedInputStream(fis, 100000);
		final var dis = new DataInputStream(bis);
		final var idLength = (short) dis.read();
		dis.read();
		final var imageType = (short) dis.read();
		flipEndian(dis.readShort());
		flipEndian(dis.readShort());
		dis.read();
		flipEndian(dis.readShort());
		flipEndian(dis.readShort());
		if(imageType != 2) throw new IOException("Supports uncompressed RGB(A) TGA images");
		width = flipEndian(dis.readShort());
		height = flipEndian(dis.readShort());
		pixelDepth = (short) dis.read();
		texWidth = width;
		texHeight = height;
		final var imageDescriptor = (short) dis.read();
		if((imageDescriptor & 0x20) == 0x0) flipped = !flipped;
		if(idLength > 0) bis.skip(idLength);
		byte[] rawData = null;
		if(pixelDepth == 32) {
			rawData = new byte[texWidth * texHeight * 4];
		}else {
			if(pixelDepth != 24) throw new RuntimeException("Only 24 and 32 bit TGAs are supported");
			rawData = new byte[texWidth * texHeight * 3];
		}
		if(pixelDepth == 24) {
			if(flipped) for(var i = height - 1; i >= 0; --i) for(var j = 0; j < width; ++j) {
				blue = dis.readByte();
				green = dis.readByte();
				red = dis.readByte();
				final var ofs = (j + i * texWidth) * 3;
				rawData[ofs] = red;
				rawData[ofs + 1] = green;
				rawData[ofs + 2] = blue;
			}
			else for(var i = 0; i < height; ++i) for(var j = 0; j < width; ++j) {
				blue = dis.readByte();
				green = dis.readByte();
				red = dis.readByte();
				final var ofs = (j + i * texWidth) * 3;
				rawData[ofs] = red;
				rawData[ofs + 1] = green;
				rawData[ofs + 2] = blue;
			}
		}else if(pixelDepth == 32) if(flipped) for(var i = height - 1; i >= 0; --i) for(var j = 0; j < width; ++j) {
			blue = dis.readByte();
			green = dis.readByte();
			red = dis.readByte();
			alpha = dis.readByte();
			final var ofs = (j + i * texWidth) * 4;
			rawData[ofs] = red;
			rawData[ofs + 1] = green;
			rawData[ofs + 2] = blue;
			rawData[ofs + 3] = alpha;
			if(alpha == 0) {
				rawData[ofs + 2] = 0;
				rawData[ofs] = rawData[ofs + 1] = 0;
			}
		}
		else for(var i = 0; i < height; ++i) for(var j = 0; j < width; ++j) {
			blue = dis.readByte();
			green = dis.readByte();
			red = dis.readByte();
			alpha = dis.readByte();
			final var ofs = (j + i * texWidth) * 4;
			if(ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
			}
			rawData[ofs] = red;
			rawData[ofs + 1] = green;
			rawData[ofs + 2] = blue;
			rawData[ofs + 3] = alpha;
			if(alpha == 0) {
				rawData[ofs + 2] = 0;
				rawData[ofs] = rawData[ofs + 1] = 0;
			}
		}
		fis.close();
		final var scratch = ByteBuffer.allocateDirect(rawData.length).order(ByteOrder.nativeOrder());
		scratch.put(rawData);
		final var perPixel = pixelDepth / 8;
		if(height < texHeight - 1) {
			final var topOffset = (texHeight - 1) * texWidth * perPixel;
			final var bottomOffset = (height - 1) * texWidth * perPixel;
			for(var x = 0; x < texWidth * perPixel; ++x) {
				scratch.put(topOffset + x, scratch.get(x));
				scratch.put(bottomOffset + texWidth * perPixel + x, scratch.get(texWidth * perPixel + x));
			}
		}
		if(width < texWidth - 1) for(var y = 0; y < texHeight; ++y) for(var k = 0; k < perPixel; ++k) {
			scratch.put((y + 1) * texWidth * perPixel - perPixel + k, scratch.get(y * texWidth * perPixel + k));
			scratch.put(y * texWidth * perPixel + width * perPixel + k, scratch.get(y * texWidth * perPixel + (width - 1) * perPixel + k));
		}
		scratch.flip();
		final var fmt = pixelDepth == 32 ? RGBA : RGB;
		return new ImageData(scratch, fmt, texWidth, texHeight);
	}

	private short flipEndian(final short signedShort) {
		final var input = signedShort & 0xFFFF;
		return (short) (input << 8 | (input & 0xFF00) >>> 8);
	}

}
