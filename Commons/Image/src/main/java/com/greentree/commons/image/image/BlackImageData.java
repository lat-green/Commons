package com.greentree.commons.image.image;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.PixelFormat;

import java.nio.ByteBuffer;

public final class BlackImageData implements ImageData {

    public static final ImageData INSTANCE = new BlackImageData();

    private BlackImageData() {
    }

    @Override
    public Color getColor(int x, int y) {
        return Color.black;
    }

    @Override
    public PixelFormat getFormat() {
        return PixelFormat.RED;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public void getData(ByteBuffer buffer) {
        buffer.put((byte) 0);
    }

    @Override
    public int getHeight() {
        return 1;
    }

}
