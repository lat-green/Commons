package com.greentree.commons.image;

import com.greentree.commons.image.image.PixelFormatConverter;

public enum PixelFormat {

	RED(1, false),
	BLUE(1, false),
	GREEN(1, false),
	ALPHA(1,true),



	LUMINANCE(1, false),
	LUMINANCE_ALPHA(1, true),
	RGB(RED, GREEN, BLUE),
	ARGB(ALPHA, RED, GREEN, BLUE),
	RGBA(RED, GREEN, BLUE, ALPHA),
	ABGR(ALPHA, BLUE, GREEN, RED),
	BGRA(BLUE, GREEN, RED, ALPHA),
	BGR(BLUE, GREEN, RED);

	public final boolean hasAlpha;
	public final int numComponents;

	PixelFormat(final int numComponents, final boolean hasAlpha) {
		this.numComponents = numComponents;
		this.hasAlpha = hasAlpha;
	}

	PixelFormat(final PixelFormat...formats) {
		boolean hasAlpha = false;
		int numComponents = 0;

		for(var format : formats) {
			hasAlpha |= format.hasAlpha;
			numComponents += format.numComponents;
		}

		this.numComponents = numComponents;
		this.hasAlpha = hasAlpha;
	}

	public int getNumComponents() {
		return numComponents;
	}

	public Color toColor(byte[] bs, int start) {
		final var color = ImageUtil.to_floats(PixelFormatConverter.convert(bs, start, this, PixelFormat.RGBA));
		return new Color(color[0], color[1], color[2], color[3]);
	}
	
	public boolean isHasAlpha() {
		return hasAlpha;
	}

}