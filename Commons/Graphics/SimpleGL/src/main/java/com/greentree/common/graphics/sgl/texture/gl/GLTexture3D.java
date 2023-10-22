package com.greentree.common.graphics.sgl.texture.gl;

import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLTexParam_int.*;
import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;

public abstract class GLTexture3D extends GLTexture {

	@Override
	public final void setWrap(final GLWrapping wrap) {
		setWrapX(wrap);
		setWrapY(wrap);
		setWrapZ(wrap);
	}

	public void setWrapX(final GLWrapping wrap) {
		glTexParameteri(getTextureTarget().glEnum(), TEXTURE_WRAP_S.glEnum, wrap.glEnum);
	}

	public void setWrapY(final GLWrapping wrap) {
		glTexParameteri(getTextureTarget().glEnum(), TEXTURE_WRAP_T.glEnum, wrap.glEnum);
	}

	public void setWrapZ(final GLWrapping wrap) {
		glTexParameteri(getTextureTarget().glEnum(), TEXTURE_WRAP_R.glEnum, wrap.glEnum);
	}
	
}
