package com.greentree.common.graphics.sgl.texture.builder;

import java.nio.ByteBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture1DImpl;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture3DImpl;

public abstract class TextureBuilder {
	
	protected GLType type = GLType.UNSIGNED_BYTE;
	protected final GLPixelFormat format;
	
	public TextureBuilder(GLPixelFormat format) {
		this.format = format;
	}

	public TextureBuilder hdr() {
		type = GLType.FLOAT;
		return this;
	}
	
	public static ByteTextureBuilder builder(ByteBuffer buffer, GLPixelFormat format) {
		return new ByteTextureBuilder(buffer, format);
	}
	
	public static EmptyTextureBuilder builder(GLPixelFormat format) {
		return new EmptyTextureBuilder(format);
	}

	
	
	public abstract GLTexture1DImpl build1d(int size, GLPixelFormat internalformat);
	public abstract GLTexture2DImpl build2d(int width, int height, GLPixelFormat internalformat);
	public abstract GLTexture3DImpl build3d(int width, int height, int depth, GLPixelFormat internalformat);
	
}
