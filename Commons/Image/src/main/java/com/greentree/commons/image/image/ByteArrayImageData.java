package com.greentree.commons.image.image;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.PixelFormat;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

public final class ByteArrayImageData implements ImageData {

    private static final long serialVersionUID = 1L;

    private final byte[] data;
    private final PixelFormat format;
    private final int width, height;

    public ByteArrayImageData(ByteBuffer buffer, PixelFormat format, int width, int height) {
        this(array(buffer), format, width, height);
    }

    public ByteArrayImageData(byte[] bytes, PixelFormat format, int width, int height) {
        Objects.requireNonNull(bytes);
        Objects.requireNonNull(format);
        if (width <= 0)
            throw new IllegalArgumentException(String.format("width mast by positive %d", width));
        if (height <= 0)
            throw new IllegalArgumentException(String.format("width mast by positive %d", height));
        if (width * height * format.numComponents != bytes.length)
            throw new IllegalArgumentException(
                    String.format("size:%dx%d %d data.length:%d", width, height, format.numComponents, bytes.length));
        data = bytes;
        this.format = format;
        this.width = width;
        this.height = height;
    }

    private static byte[] array(ByteBuffer buffer) {
        final var data = new byte[buffer.capacity()];
        buffer.get(data);
        return data;
    }

    public ByteArrayImageData(Color color) {
        width = 1;
        height = 1;
        if (color.hasAlpha()) {
            data = new byte[]{color.getRedByte(), color.getGreenByte(), color.getBlueByte(),
                    color.getAlphaByte(),};
            format = PixelFormat.RGBA;
        } else {
            data = new byte[]{color.getRedByte(), color.getGreenByte(), color.getBlueByte(),};
            format = PixelFormat.RGB;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(data);
        result = prime * result + Objects.hash(format, height, width);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ByteArrayImageData other = (ByteArrayImageData) obj;
        return Arrays.equals(data, other.data) && format == other.format && height == other.height
                && width == other.width;
    }

    @Override
    public String toString() {
        final var str = (data.length < 12) ? Arrays.toString(data) : "...";
        return "ByteArrayImageData [data=[" + str + "], format=" + format + ", width=" + width + ", height="
                + height + "]";
    }

    public Color getColor(int x, int y) {
        var index = (x + y * getWidth()) * getFormat().numComponents;
        return format.toColor(data, index);
    }

    public PixelFormat getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public void getData(ByteBuffer buffer) {
        buffer.put(data);
        buffer.flip();
    }

    public int getHeight() {
        return height;
    }

    @Override
    public byte[] getDataArray() {
        return data;
    }

}
