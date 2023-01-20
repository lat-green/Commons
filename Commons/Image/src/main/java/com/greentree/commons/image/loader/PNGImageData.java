package com.greentree.commons.image.loader;

import static com.greentree.commons.image.PixelFormat.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.greentree.commons.image.image.ImageData;
public class PNGImageData implements ImageDataLoader {


	public final static PNGImageData IMAGE_DATA_LOADER = new PNGImageData();

	private PNGImageData() {
	}

	@Override
	public ImageData loadImage(final InputStream fis) throws IOException {
		ByteBuffer scratch;
		final PNGDecoder decoder = new PNGDecoder(fis);
		if(!decoder.isRGB()) throw new IOException("Only RGB formatted images are supported by the PNGLoader");
		final var width = decoder.getWidth();
		final var height = decoder.getHeight();
		final int perPixel = decoder.hasAlpha() ? 4 : 3;
		final var fmt = perPixel == 4 ? RGBA : RGB;
		decoder.decode(scratch = ByteBuffer.allocateDirect(width * height * perPixel).order(ByteOrder.nativeOrder()), width * perPixel, fmt);
		if(height < height - 1) {
			final int topOffset = (height - 1) * width * perPixel;
			final int bottomOffset = (height - 1) * width * perPixel;
			for(int x = 0; x < width; ++x) for(int i = 0; i < perPixel; ++i) {
				scratch.put(topOffset + x + i, scratch.get(x + i));
				scratch.put(bottomOffset + width * perPixel + x + i, scratch.get(bottomOffset + x + i));
			}
		}
		if(width < width - 1) for(int y = 0; y < height; ++y) for(int j = 0; j < perPixel; ++j) {
			scratch.put((y + 1) * width * perPixel - perPixel + j, scratch.get(y * width * perPixel + j));
			scratch.put(y * width * perPixel + width * perPixel + j,
					scratch.get(y * width * perPixel + (width - 1) * perPixel + j));
		}
		scratch.position(0);
		return new ImageData(scratch, fmt, width, height);
	}
}
