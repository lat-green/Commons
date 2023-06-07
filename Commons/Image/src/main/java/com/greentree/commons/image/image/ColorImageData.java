package com.greentree.commons.image.image;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.PixelFormat;

import java.nio.ByteBuffer;

public class ColorImageData implements ImageData {

    private final Color color;
    private final PixelFormat format;

    public ColorImageData(Color color, PixelFormat format) {
        this.color = color;
        this.format = format;
    }

    public ColorImageData(Color color) {
        this.color = color;
        if (color.hasAlpha())
            this.format = PixelFormat.RGBA;
        else
            this.format = PixelFormat.RGB;
    }

    @Override
    public Color getColor(int x, int y) {
        return color;
    }

    @Override
    public PixelFormat getFormat() {
        return format;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public void getData(ByteBuffer buffer) {
        format.write(color, buffer);
    }

    @Override
    public int getHeight() {
        return 1;
    }

}
