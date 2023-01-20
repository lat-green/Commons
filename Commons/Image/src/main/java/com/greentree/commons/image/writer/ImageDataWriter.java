package com.greentree.commons.image.writer;

import java.io.IOException;
import java.io.OutputStream;

import com.greentree.commons.image.image.ImageData;

public interface ImageDataWriter {

	void write(ImageData img, OutputStream out) throws IOException;
	
}
