package com.greentree.common.graphics.sgl.freambuffer.attachment;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;

public class Texture2DAttachment extends TextureAttachment<GLTexture2DImpl> {
	
	public Texture2DAttachment(GLTexture2DImpl texture, GLPixelFormat internalformat) {
		super(texture, internalformat);
	}
	
	@Override
	public void resize(int width, int height) {
		getTexture().bind();
		
		getTexture().setData(getInternalformat(), width, height);
		
		GLTexture2DImpl.BINDER.unbind();
	}
	
}
