package com.greentree.common.graphics.sgl.freambuffer.attachment;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;

public class CubeMapTextureAttachment extends TextureAttachment<GLCubeMapTexture> {
	
	public CubeMapTextureAttachment(GLCubeMapTexture texture, GLPixelFormat format) {
		super(texture, format);
	}
	
	@Override
	public void resize(int width, int height) {
		getTexture().bind();
		final var internalFormat = getInternalformat();
		
		for(var dir : getTexture().dirs())
			dir.setData(internalFormat, width, height);
		
		getTexture().generateMipmap();
		
		GLCubeMapTexture.BINDER.unbind();
	}
	
	@Override
	public String toString() {
		return "CubeMapTextureAttachment [internalformat=" + getInternalformat() + ", texture=" + getTexture() + "]";
	}
	
}
