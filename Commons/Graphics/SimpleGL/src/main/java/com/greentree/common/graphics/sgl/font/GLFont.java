package com.greentree.common.graphics.sgl.font;

public interface GLFont {
	
	void drawString(float x, float y, String text);

	int getHeight();
	
	default int getHeight(String text) {
		return getHeight();
	}
	
	int getWidth(String text);
	
}
