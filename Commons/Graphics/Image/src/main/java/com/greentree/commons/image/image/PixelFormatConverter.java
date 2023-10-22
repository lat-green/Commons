package com.greentree.commons.image.image;

import com.greentree.commons.image.PixelFormat;
import com.greentree.commons.util.collection.Table;

import java.util.Arrays;

public class PixelFormatConverter {

    private static final Table<PixelFormat, PixelFormat, FormatConverter> table = new Table<>();

    static {
        table.put(PixelFormat.RGB, PixelFormat.BGR, PixelFormatConverter::convertRGBtoBGR);
        table.put(PixelFormat.RGB, PixelFormat.RGBA, PixelFormatConverter::convertRGBtoRGBA);
        table.put(PixelFormat.RGB, PixelFormat.ABGR, PixelFormatConverter::convertRGBtoABGR);
        table.put(PixelFormat.RGBA, PixelFormat.ABGR, PixelFormatConverter::convertRGBAtoABGR);
        table.put(PixelFormat.RGBA, PixelFormat.RGB, PixelFormatConverter::convertRGBAtoRGB);
        for (var pf : PixelFormat.values())
            table.put(pf, pf, (arr) -> {
                return Arrays.copyOf(arr, arr.length);
            });
    }

    public static byte[] convert(byte[] byes, int index, PixelFormat in, PixelFormat out) {
        var c = table.get(in, out);
        var result = new byte[out.numComponents];
        c.convert(byes, in.numComponents, result, out.numComponents, index);
        return result;
    }

    public static byte[] convert(byte[] byes, PixelFormat in, PixelFormat out) {
        var c = getConverter(in, out);
        if (c == null)
            throw new IllegalArgumentException("in: " + in + " out:" + out);
        var numPixels = byes.length / in.numComponents;
        var result = new byte[numPixels * out.numComponents];
        for (int i = 0; i < numPixels; i++) {
            c.convert(byes, in.numComponents, result, out.numComponents, i);
        }
        return result;
    }

    private static FormatConverter getConverter(PixelFormat in, PixelFormat out) {
        if (in == out)
            return new FormatConverter() {
                @Override
                public byte[] convert(byte[] in) {
                    return in;
                }
            };
        var c = table.get(in, out);
        return c;
    }

    private static byte[] convertRGBtoABGR(byte[] in) {
        return convertRGBAtoABGR(convertRGBtoRGBA(in));
    }

    private static byte[] convertRGBAtoABGR(byte[] in) {
        PixelFormatChecker.check(PixelFormat.RGBA, in);
        return new byte[]{
                in[3], in[2], in[1], in[0]
        };
    }

    private static byte[] convertRGBtoRGBA(byte[] in) {
        PixelFormatChecker.check(PixelFormat.RGB, in);
        return new byte[]{
                in[0], in[1], in[2], -128
        };
    }

    private static byte[] convertRGBAtoRGB(byte[] in) {
        PixelFormatChecker.check(PixelFormat.RGBA, in);
        return new byte[]{
                in[0], in[1], in[2]
        };
    }

    private static byte[] convertRGBtoBGR(byte[] in) {
        PixelFormatChecker.check(PixelFormat.RGB, in);
        return new byte[]{
                in[2], in[1], in[0]
        };
    }

    @FunctionalInterface
    private interface FormatConverter {

        default void convert(byte[] src, int srcComponentCount, byte[] dest, int destComponentCount, int index) {
            var copy = Arrays.copyOfRange(src, srcComponentCount * index, srcComponentCount + srcComponentCount * index);
            var res = convert(copy);
            System.arraycopy(res, 0, dest, destComponentCount * index, destComponentCount);
        }

        byte[] convert(byte[] in);

    }

}
