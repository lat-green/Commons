package com.greentree.common.graphics.sgl.texture.builder;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture1DImpl;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2D;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture3DImpl;
import com.greentree.commons.math.Mathf;

public class ByteTextureBuilder extends TextureBuilder {
	
	private ByteBuffer buffer;
	
	public ByteTextureBuilder(ByteBuffer buffer, GLPixelFormat format) {
		super(format);
		this.buffer = buffer;
	}
	
	@Override
	public GLTexture1DImpl build1d(int size, GLPixelFormat internalformat) {
		size = Mathf.get2Fold(size);
		
		var tex = new GLTexture1DImpl();
		
		tex.bind();
		
		glTexImage1D(GLTexture1DImpl.GL_TEXTURE_TARGET.glEnum, 0, internalformat.glEnum, size, 0,
				format.glEnum, type.glEnum, buffer);
		glGenerateMipmap(GLTexture1DImpl.GL_TEXTURE_TARGET.glEnum);
		
		tex.unbind();
		
		return tex;
	}
	
	public GLTexture2D build2d(int width, int height) {
		return build2d(width, height, GLPixelFormat.RGBA);
	}
	
	@Override
	public GLTexture2DImpl build2d(int width, int height, GLPixelFormat internalformat) {
		var tex = new GLTexture2DImpl();
		
		tex.bind();
		
		glTexImage2D(GLTexture2DImpl.GL_TEXTURE_TARGET.glEnum, 0, internalformat.glEnum, width,
				height, 0, format.glEnum, type.glEnum, buffer);
		glGenerateMipmap(GLTexture2DImpl.GL_TEXTURE_TARGET.glEnum);
		
		tex.unbind();
		
		return tex;
	}
	
	@Override
	public GLTexture3DImpl build3d(int width, int height, int depth, GLPixelFormat internalformat) {
		width = Mathf.get2Fold(width);
		height = Mathf.get2Fold(height);
		depth = Mathf.get2Fold(depth);
		
		var tex = new GLTexture3DImpl();
		
		tex.bind();
		
		glTexImage3D(GLTexture3DImpl.GL_TEXTURE_TARGET.glEnum, 0, internalformat.glEnum, width,
				height, depth, 0, format.glEnum, type.glEnum, buffer);
		glGenerateMipmap(GLTexture3DImpl.GL_TEXTURE_TARGET.glEnum);
		
		tex.unbind();
		
		return tex;
	}
	
}
