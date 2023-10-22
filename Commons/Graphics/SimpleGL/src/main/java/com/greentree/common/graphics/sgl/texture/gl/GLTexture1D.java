package com.greentree.common.graphics.sgl.texture.gl;

import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLTexParam_int.*;
import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;

public abstract class GLTexture1D extends GLTexture {
	
	public GLTexture1D() {
		super();
	}
	
	public void setWrap(final GLWrapping wrap) {
		setWrapX(wrap);
	}
	
	public void setWrapX(final GLWrapping wrap) {
		glTexParameteri(getTextureTarget().glEnum(), TEXTURE_WRAP_S.glEnum, wrap.glEnum);
	}
	
}
