package com.greentree.common.image.writer;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.greentree.common.image.ImageIODecoder;
import com.greentree.common.image.image.ImageData;

public class ImageIOImageDataWriter implements ImageDataWriter {

	public static final ImageIOImageDataWriter INSTANCE = new ImageIOImageDataWriter();
	
	private ImageIOImageDataWriter() {
	}
	
	@Override
	public void write(ImageData img, OutputStream out) throws IOException {
		final var bimg = ImageIODecoder.toBufferedImage(img);
		ImageIO.write(bimg, "bmp", out);
	}
	
}
