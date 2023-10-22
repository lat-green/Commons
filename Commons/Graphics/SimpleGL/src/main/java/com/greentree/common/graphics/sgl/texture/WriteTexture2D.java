package com.greentree.common.graphics.sgl.texture;

import java.nio.ByteBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;

public interface WriteTexture2D {

	void setData(GLPixelFormat internalFormat, int width, int height);
	void setData(GLPixelFormat internalFormat, int width, int height, GLPixelFormat format, ByteBuffer buffer);

}
