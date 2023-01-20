package com.greentree.common.image.writer;

import java.io.IOException;
import java.io.OutputStream;

import com.greentree.common.image.image.ImageData;

public interface ImageDataWriter {

	void write(ImageData img, OutputStream out) throws IOException;
	
}
