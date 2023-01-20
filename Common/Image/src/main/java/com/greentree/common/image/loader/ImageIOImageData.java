package com.greentree.common.image.loader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.greentree.common.image.ImageIODecoder;
import com.greentree.common.image.image.ImageData;

public class ImageIOImageData implements ImageDataLoader {

	public final static ImageIOImageData IMAGE_DATA_LOADER = new ImageIOImageData();

	private ImageIOImageData() {
	}

	@Deprecated
	public ImageData loadImage(final BufferedImage image) {
		return ImageIODecoder.toImageData(image);
	}

	@Override
	public ImageData loadImage(final InputStream fis) throws IOException {
		final BufferedImage bufferedImage = ImageIO.read(fis);
		return ImageIODecoder.toImageData(bufferedImage);
	}

}
