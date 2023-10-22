package com.greentree.commons.image;

import com.greentree.commons.image.image.ByteArrayImageData;
import com.greentree.commons.image.image.ImageData;

import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;

import static java.awt.color.ColorSpace.CS_sRGB;

public class ImageIODecoder {

    private static final ColorModel glAlphaColorModel;
    private static final ColorModel glColorModel;

    static {
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(CS_sRGB), new int[]{8, 8, 8, 8}, true, false, 3, 0);
        glColorModel = new ComponentColorModel(ColorSpace.getInstance(CS_sRGB), new int[]{8, 8, 8, 0}, false, false, 1, 0);
    }

    public static ImageData toImageData(BufferedImage image) {
        var texWidth = image.getWidth();
        var texHeight = image.getHeight();
//        var format = type(image.getColorModel().getColorSpace().getType());
        var pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        var buffer = ByteBuffer.allocateDirect(pixels.length * 4);
//        var buffer = ByteBuffer.allocateDirect(pixels.length * format.numComponents);
        for (int pixel : pixels) {
            buffer.put((byte) ((pixel >> 16) & 0xFF));
            buffer.put((byte) ((pixel >> 8) & 0xFF));
            buffer.put((byte) (pixel & 0xFF));
//            if (useAlpha)
            buffer.put((byte) ((byte) (pixel >> 24) & 0xFF));
        }
        buffer.flip();
//        if (useAlpha)
        return new ByteArrayImageData(buffer, PixelFormat.RGBA, texWidth, texHeight);
//        return new ByteArrayImageData(buffer, PixelFormat.RGB, texWidth, texHeight);
    }

    public static BufferedImage toBufferedImage(ImageData img) {
        if (img.getFormat().hasAlpha) {
            var bb = img.toFormat(PixelFormat.ABGR).getDataArray();
            var result = new BufferedImage(img.getWidth(), img.getHeight(), toImageIOType(img.getFormat()));
            var raster = Raster.createRaster(result.getSampleModel(), new DataBufferByte(bb, bb.length), null);
            result.setData(raster);
            return result;
        }
        var bb = img.toFormat(PixelFormat.BGR).getDataArray();
        var result = new BufferedImage(img.getWidth(), img.getHeight(), toImageIOType(img.getFormat()));
        var raster = Raster.createRaster(result.getSampleModel(), new DataBufferByte(bb, bb.length), null);
        result.setData(raster);
        return result;
    }

    public static int toImageIOType(PixelFormat format) {
        return switch (format) {
            case RGB -> BufferedImage.TYPE_3BYTE_BGR;
            case RGBA -> BufferedImage.TYPE_4BYTE_ABGR;
            default -> throw new IllegalArgumentException("Unexpected value: " + format);
        };
    }

    private static PixelFormat type(int format) {
        return switch (format) {
            case BufferedImage.TYPE_3BYTE_BGR -> PixelFormat.BGR;
            case BufferedImage.TYPE_4BYTE_ABGR -> PixelFormat.ABGR;
            case BufferedImage.TYPE_BYTE_GRAY -> PixelFormat.LUMINANCE;
            default -> throw new IllegalArgumentException("Unexpected value: " + format);
        };
    }

}
