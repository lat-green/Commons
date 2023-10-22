package com.greentree.common.graphics.sgl.texture.gl;

import static com.greentree.common.graphics.sgl.enums.gl.GLType.*;
import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLTexParam_int.*;
import static com.greentree.common.graphics.sgl.enums.gl.target.GLTexture2DEnumTarget.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;
import com.greentree.common.graphics.sgl.enums.gl.target.GLTexture2DEnumTarget;

public final class GLTexture2DImpl extends GLTexture2D {
	
	public static final GLTexture2DEnumTarget GL_TEXTURE_TARGET = TEXTURE_2D;
	
	public static final Binder BINDER = new Binder();
	
	
	@Deprecated
	public static void unbinds() {
		BINDER.unbind();
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}
	
	
	@Override
	public GLTexture2DEnumTarget getTextureTarget() {
		return GL_TEXTURE_TARGET;
	}
	
	@Override
	public void setData(GLPixelFormat internalFormat, int width, int height) {
		glTexImage2D(GL_TEXTURE_TARGET.glEnum, 0, internalFormat.glEnum, width, height, 0, internalFormat.base().glEnum,
				UNSIGNED_BYTE.glEnum, 0);
		glGenerateMipmap(GL_TEXTURE_TARGET.glEnum);
	}
	
	@Override
	public void setData(GLPixelFormat internalFormat, int width, int height, GLPixelFormat format, ByteBuffer buffer) {
		glTexImage2D(GL_TEXTURE_TARGET.glEnum, 0, internalFormat.glEnum, width, height, 0, format.glEnum,
				UNSIGNED_BYTE.glEnum, buffer);
		glGenerateMipmap(GL_TEXTURE_TARGET.glEnum);
	}
	
	public void setWrapX(final GLWrapping wrap) {
		glTexParameteri(GL_TEXTURE_TARGET.glEnum, TEXTURE_WRAP_S.glEnum, wrap.glEnum);
	}
	
	public void setWrapY(final GLWrapping wrap) {
		glTexParameteri(GL_TEXTURE_TARGET.glEnum, TEXTURE_WRAP_T.glEnum, wrap.glEnum);
	}

	@Override
	public int getWidth() {
		return glGetTexLevelParameteri(GL_TEXTURE_TARGET.glEnum, 0, GL_TEXTURE_WIDTH);
	}

	@Override
	public int getHeight() {
		return glGetTexLevelParameteri(GL_TEXTURE_TARGET.glEnum, 0, GL_TEXTURE_HEIGHT);
	}

	public static final class Binder extends GLObjectBinder {
		
		private Binder() {
		}
		
		@Override
		protected void glBind(int glID) {
			glBindTexture(GL_TEXTURE_TARGET.glEnum, glID);
		}
		
	}
	
}
