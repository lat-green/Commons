package com.greentree.common.graphics.sgl.texture.gl;


import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLTexParam_int.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLWrapping;
import com.greentree.common.graphics.sgl.enums.gl.target.GLTextureEnumTarget;

public abstract class GLTexture extends GLObject {
	
	protected GLTexture() {
		super(glGenTextures());
		bind();
		unbind();
	}
	
	public void generateMipmap() {
		glGenerateMipmap(getTextureTarget().glEnum());
	}
	
	public void bind(int slot) {
		glActiveTexture(GL_TEXTURE0 + slot);
		bind();
		glActiveTexture(GL_TEXTURE0);
	}
	
	@Override
	public void delete() {
		glDeleteTextures(glID);
	}
	
	public GLTextureEnumTarget getTextureTarget() {
		throw new UnsupportedOperationException();
	}
	
	public final void setFilter(GLFiltering filtering) {
		setMagFilter(filtering);
		setMinFilter(filtering);
	}
	
	public void setMagFilter(final GLFiltering filtering) {
		glTexParameteri(getTextureTarget().glEnum(), TEXTURE_MAG_FILTER.glEnum, filtering.glEnum);
	}
	
	public void setMinFilter(final GLFiltering filtering) {
		glTexParameteri(getTextureTarget().glEnum(), TEXTURE_MIN_FILTER.glEnum, filtering.glEnum);
	}
	
	public abstract void setWrap(GLWrapping wrap);
	
}
