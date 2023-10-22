package com.greentree.commons.image.loader;

import com.greentree.commons.image.ImageIODecoder;
import com.greentree.commons.image.image.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageIOImageLoader implements ImageLoader {

    public final static ImageIOImageLoader IMAGE_DATA_LOADER = new ImageIOImageLoader();

    private ImageIOImageLoader() {
    }

    @Override
    public ImageData loadImage(final InputStream fis) throws IOException {
        final BufferedImage bufferedImage = ImageIO.read(fis);
        return ImageIODecoder.toImageData(bufferedImage);
    }

}
