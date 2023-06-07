package com.greentree.commons.image.writer;

import com.greentree.commons.image.image.ByteArrayImageData;

import java.io.IOException;
import java.io.OutputStream;

public interface ImageDataWriter {

    void write(ByteArrayImageData img, OutputStream out) throws IOException;

}
