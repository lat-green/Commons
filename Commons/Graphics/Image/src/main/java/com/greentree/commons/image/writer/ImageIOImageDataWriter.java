package com.greentree.commons.image.writer;

import com.greentree.commons.image.ImageIODecoder;
import com.greentree.commons.image.image.ByteArrayImageData;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

public class ImageIOImageDataWriter implements ImageDataWriter {

    public static final ImageIOImageDataWriter INSTANCE = new ImageIOImageDataWriter();

    private ImageIOImageDataWriter() {
    }

    @Override
    public void write(ByteArrayImageData img, OutputStream out) throws IOException {
        final var bimg = ImageIODecoder.toBufferedImage(img);
        ImageIO.write(bimg, "bmp", out);
    }

}
