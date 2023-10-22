package com.greentree.common.graphics.sgl.texture.gl;

import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;
import com.greentree.common.graphics.sgl.texture.WriteTexture2D;

public abstract class GLTexture2D extends GLTexture implements WriteTexture2D {
	
	public GLTexture2D() {
	}
	
	@Override
	public void setWrap(final GLWrapping wrap) {
		setWrapX(wrap);
		setWrapY(wrap);
	}
	
	public abstract void setWrapX(final GLWrapping wrap);
	
	public abstract void setWrapY(final GLWrapping wrap);

    public abstract int getWidth();

	public abstract int getHeight();

}
