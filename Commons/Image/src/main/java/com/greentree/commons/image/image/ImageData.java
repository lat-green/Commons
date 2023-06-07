package com.greentree.commons.image.image;

import com.greentree.commons.image.Color;
import com.greentree.commons.image.PixelFormat;

import java.io.Serializable;
import java.nio.ByteBuffer;

public interface ImageData extends Serializable {

    Color getColor(int x, int y);

    default ImageData subImage(int x, int y, int w, int h) {
        final var numc = getFormat().numComponents;
        final var data = getDataArray();
        final var bytes = new byte[w * h * numc];
        for (int j = 0; j < h; j++)
            for (int i = 0; i < w; i++)
                for (int c = 0; c < numc; c++) {
                    final var out = (i + j * w) * numc + c;
                    final var in = (i + x + (j + y) * getWidth()) * numc + c;
                    bytes[out] = data[in];
                }
        return new ByteArrayImageData(bytes, getFormat(), w, h);
    }

    default ImageData toFormat(PixelFormat format) {
        if (format == getFormat())
            return this;
        return new ByteArrayImageData(PixelFormatConverter.convert(getDataArray(), getFormat(), format), format, getWidth(), getHeight());
    }

    PixelFormat getFormat();

    default ByteBuffer getData() {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(getDataLength());
        getData(buffer);
        buffer.flip();
        return buffer;
    }

    int getWidth();

    default int getDataLength() {
        return getFormat().numComponents * getWidth() * getHeight();
    }

    void getData(ByteBuffer buffer);

    int getHeight();

    default byte[] getDataArray() {
        var buffer = getData();
        var data = new byte[getDataLength()];
        buffer.get(data);
        return data;
    }

}
