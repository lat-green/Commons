package com.greentree.common.image;

import static java.awt.color.ColorSpace.CS_sRGB;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

import com.greentree.common.image.image.ImageData;

public class ImageIODecoder {

	private static final ColorModel glAlphaColorModel;
	private static final ColorModel glColorModel;
	static {
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(CS_sRGB), new int[]{8,8,8,8}, true, false, 3, 0);
		glColorModel = new ComponentColorModel(ColorSpace.getInstance(CS_sRGB), new int[]{8,8,8,0}, false, false, 1, 0);
	}
	
	public static ImageData toImageData(BufferedImage image) {
		ByteBuffer imageBuffer;
		final int texWidth = image.getWidth();
		final int texHeight = image.getHeight();
		final boolean useAlpha = image.getColorModel().hasAlpha();
		BufferedImage texImage;
		if(useAlpha) {
			final WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 4, null);
			texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<>());
		}else {
			final WritableRaster raster = Raster.createInterleavedRaster(0, texWidth, texHeight, 3, null);
			texImage = new BufferedImage(glColorModel, raster, false, new Hashtable<>());
		}
		final Graphics2D g = (Graphics2D) texImage.getGraphics();
		if(useAlpha) {
			g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
			g.fillRect(0, 0, texWidth, texHeight);
		}

		g.drawImage(image, 0, 0, null);

		final byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();
		g.dispose();
		if(useAlpha)
			return new ImageData(imageBuffer, PixelFormat.RGBA, texWidth, texHeight);
		else
			return new ImageData(imageBuffer, PixelFormat.RGB, texWidth, texHeight);
	}

	public static BufferedImage toBufferedImage(ImageData img) {
		final var bi = new BufferedImage(img.getWidth(), img.getHeight(), toImageIOType(img.getFormat()));
		final var bb = img.getByteBuffer();
        final int bytesPerPixel = 3;
        byte[] imageArray = ((DataBufferByte) bi.getRaster()
                .getDataBuffer()).getData();
        bb.rewind();
        bb.get(imageArray);
        int numPixels = bb.capacity() / bytesPerPixel;
        for (int i = 0; i < numPixels; i++) {
            byte tmp = imageArray[i * bytesPerPixel];
            imageArray[i * bytesPerPixel] = imageArray[i * bytesPerPixel
                    + 2];
            imageArray[i * bytesPerPixel + 2] = tmp;
        }
        return bi;
	}

	public static int toImageIOType(PixelFormat format) {
		return switch(format) {
			case RGB -> BufferedImage.TYPE_3BYTE_BGR;
			case RGBA -> BufferedImage.TYPE_4BYTE_ABGR;
			default -> throw new IllegalArgumentException("Unexpected value: " + format);
		};
	}
}
