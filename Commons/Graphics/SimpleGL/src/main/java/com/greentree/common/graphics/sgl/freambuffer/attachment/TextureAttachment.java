package com.greentree.common.graphics.sgl.freambuffer.attachment;



import static com.greentree.common.graphics.sgl.enums.gl.target.GLFrameBufferEnumTarget.*;
import static org.lwjgl.opengl.GL32.*;

import com.greentree.common.graphics.sgl.enums.gl.GLAttachment;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture;

public abstract class TextureAttachment<T extends GLTexture> implements Attachment {
	
	private final T texture;
	private final GLPixelFormat internalformat;
	
	public TextureAttachment(T texture, GLPixelFormat format) {
		this.texture = texture;
		this.internalformat = format;
		texture.bind();
		texture.setFilter(GLFiltering.NEAREST);
		texture.unbind();
	}
	
	@Override
	public void bind(GLAttachment GL_ATTACHMENT) {
		final var texture = getTexture();
		glFramebufferTexture(FRAMEBUFFER.glEnum, GL_ATTACHMENT.glEnum, texture.__glID(), 0);
	}
	
	@Override
	public GLPixelFormat getInternalformat() {
		return internalformat;
	}
	
	public T getTexture() {
		return texture;
	}
	
}
