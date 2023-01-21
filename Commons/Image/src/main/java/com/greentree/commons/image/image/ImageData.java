package com.greentree.commons.image.image;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.PixelFormat;

public final class ImageData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final byte[] data;
	private final PixelFormat format;
	private final int width, height;
	
	public ImageData(byte[] bytes, PixelFormat format, int width, int height) {
		if(width <= 0)
			throw new IllegalArgumentException(String.format("width mast by positive %d", width));
		if(height <= 0)
			throw new IllegalArgumentException(String.format("width mast by positive %d", height));
		if(width * height * format.numComponents != bytes.length)
			throw new IllegalArgumentException(
					String.format("size:%dx%d  data.length:%d", width, height, bytes.length));
		Objects.requireNonNull(format);
		
		data = bytes;
		this.format = format;
		this.width = width;
		this.height = height;
	}
	
	public ImageData(ByteBuffer buffer, PixelFormat format, int width, int height) {
		this(array(buffer), format, width, height);
	}
	
	public ImageData(Color color) {
		width = 1;
		height = 1;
		if(color.hasAlpha()) {
			data = new byte[]{color.getRedByte(),color.getGreenByte(),color.getBlueByte(),
					color.getAlphaByte(),};
			format = PixelFormat.RGBA;
		}else {
			data = new byte[]{color.getRedByte(),color.getGreenByte(),color.getBlueByte(),};
			format = PixelFormat.RGB;
		}
	}
	
	private static byte[] array(ByteBuffer buffer) {
		final var data = new byte[buffer.capacity()];
		buffer.get(data);
		return data;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		ImageData other = (ImageData) obj;
		return Arrays.equals(data, other.data) && format == other.format && height == other.height
				&& width == other.width;
	}
	
	public ByteBuffer getByteBuffer() {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	
	public byte[] getData() {
		return data;
	}
	
	
	public PixelFormat getFormat() {
		return format;
	}
	
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + Objects.hash(format, height, width);
		return result;
	}
	
	public Color getColor(int x, int y) {
		var index = (x + y * getWidth()) * getFormat().numComponents;
		return format.toColor(data, index);
	}
	
	public ImageData subImage(int x, int y, int w, int h) {
		final var numc = getFormat().numComponents;
		final var data = getData();
		final var bytes = new byte[w * h * numc];
		for(int j = 0; j < h; j++)
			for(int i = 0; i < w; i++)
				for(int c = 0; c < numc; c++) {
					final var out = (i + j * w) * numc + c;
					final var in = (i + x + (j + y) * getWidth()) * numc + c;
					bytes[out] = data[in];
				}
		return new ImageData(bytes, getFormat(), w, h);
	}
	
	@Override
	public String toString() {
		final var str = (data.length < 12) ? Arrays.toString(data) : "...";
		return "ImageData [data=[" + str + "], format=" + format + ", width=" + width + ", height="
				+ height + "]";
	}
	
	
	
}
