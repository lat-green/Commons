package com.greentree.commons.image;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Random;

public class Color implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color blue = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Color cyan = new Color(0.0f, 1.0f, 1.0f, 1.0f);
	public static final Color darkGray = new Color(0.3f, 0.3f, 0.3f, 1.0f);
	public static final Color gray = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color green = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color lightGray = new Color(0.7f, 0.7f, 0.7f, 1.0f);
	public static final Color magenta = new Color(1, 0, 1);
	public static final Color orange = new Color(1, 200 / 255, 0);
	public static final Color pink = new Color(1, .4f, .7f);
	public static final Color red = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color transparent = new Color(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Color white = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color yellow = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	private static final float EPS = 1E-9f;

	public final float r, g, b, a;

	public Color(final Color color) {
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
	}

	public Color(double f) {
		this(f, f, f);
	}

	public Color(double r, double g, double b) {
		this((float) r, (float) g, (float) b);
	}

	public Color(final float r, final float g, final float b) {
		a = 1.0f;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color(final float r, final float g, final float b, final float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(final float[] buffer) {
		r = buffer[0];
		g = buffer[1];
		b = buffer[2];
		a = buffer[3];
	}

	public Color(final FloatBuffer buffer) {
		r = buffer.get();
		g = buffer.get();
		b = buffer.get();
		a = buffer.get();
	}

	@Deprecated
	public Color(final int value) {
		final int r = (value & 0xFF0000) >> 16;
		final int g = (value & 0xFF00) >> 8;
		final int b = value & 0xFF;
		int a = (value & 0xFF000000) >> 24;
		if(a < 0) a += 256;
		if(a == 0) a = 255;
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}

	public static Color decode(final String nm) {
		return new Color(Integer.decode(nm));
	}

	public static Color getRandom() {
		return getRandom(0, 1);
	}

	public static Color getRandom(float min, float max) {
		float s = max - min;
		return new Color(Math.random() * s + min, Math.random() * s + min, Math.random() * s + min);
	}

	public static Color getRandom(final int i, final int j) {
		final Random rand = new Random();
		return new Color(rand.nextInt(j - i) + i, rand.nextInt(j - i) + i, rand.nextInt(j - i) + i);
	}

	public Color addToCopy(final Color c) {
		return new Color(r + c.r, g + c.g, b + c.g, a + c.r);
	}

	public Color brighter() {
		return this.brighter(0.2f);
	}

	public Color brighter(float scale) {
		++scale;
		final Color temp = new Color(r * scale, g * scale, b * scale, a);
		return temp;
	}

	public boolean hasAlpha() {
		return a < 1 - EPS;
	}

	public Color darker() {
		return this.darker(0.5f);
	}

	public Color darker(float scale) {
		scale = 1.0f - scale;
		return new Color(r * scale, g * scale, b * scale, a);
	}

	@Override
	public boolean equals(final Object other) {
		if(other instanceof Color) {
			final Color o = (Color) other;
			return o.r == r && o.g == g && o.b == b && o.a == a;
		}
		return false;
	}

	public byte getAlphaByte() {
		return (byte) (a * 255.0f);
	}

	public byte getBlueByte() {
		return (byte) (b * 255.0f);
	}

	public byte getGreenByte() {
		return (byte) (g * 255.0f);
	}

	public byte getRedByte() {
		return (byte) (r * 255.0f);
	}

	public ByteBuffer getRGB(ByteBuffer buffer) {
		buffer.put(getRedByte());
		buffer.put(getGreenByte());
		buffer.put(getBlueByte());
		buffer.position(0);
		return buffer;
	}

	public ByteBuffer getRGBA(ByteBuffer buffer) {
		buffer.put(getRedByte());
		buffer.put(getGreenByte());
		buffer.put(getBlueByte());
		buffer.put(getAlphaByte());
		buffer.position(0);
		return buffer;
	}

	@Override
	public int hashCode() {
		return (int) (r + g + b + a) * 255;
	}

	public Color multiply(final Color c) {
		return new Color(r * c.r, g * c.g, b * c.b, a * c.a);
	}

	public Color multiply(float k) {
		return new Color(r * k, g * k, b * k, a);
	}

	public Color scaleCopy(final float value) {
		return new Color(r * value, g * value, b * value, a * value);
	}

	@Override
	public String toString() {
		return String.format("Color(%f,%f,%f,%f)", r, g, b, a);
	}

}
