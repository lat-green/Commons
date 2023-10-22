package com.greentree.common.graphics.sgl.freambuffer.attachment;

import static com.greentree.common.graphics.sgl.enums.gl.target.GLFrameBufferEnumTarget.*;
import static com.greentree.common.graphics.sgl.enums.gl.target.GLRenderbufferEnumTarget.*;
import static org.lwjgl.opengl.GL30.*;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.enums.gl.GLAttachment;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;

public class RenderbuffersAttachment extends GLObject implements Attachment {
	
	public static final Binder BINDER = new Binder();
	
	private final GLPixelFormat internalformat;
	
	public RenderbuffersAttachment() {
		this(GLPixelFormat.DEPTH_COMPONENT);
		bind();
		unbind();
	}
	
	public RenderbuffersAttachment(final GLPixelFormat internalformat) {
		super(glGenRenderbuffers());
		this.internalformat = internalformat;
		bind();
		unbind();
	}
	
	@Override
	public void bind(GLAttachment GL_ATTACHMENT) {
		glFramebufferRenderbuffer(FRAMEBUFFER.glEnum, GL_ATTACHMENT.glEnum, RENDERBUFFER.glEnum, glID);
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}
	
	@Override
	public void delete() {
		glDeleteRenderbuffers(glID);
	}
	
	@Override
	public GLPixelFormat getInternalformat() {
		return internalformat;
	}
	
	@Override
	public void resize(int width, int height) {
		bind();
		glRenderbufferStorage(GL_RENDERBUFFER, internalformat.glEnum, width, height);
		unbind();
	}
	
	public static final class Binder extends GLObjectBinder {
		
		private Binder() {
		}
		
		@Override
		protected void glBind(int glID) {
			glBindRenderbuffer(RENDERBUFFER.glEnum, glID);
		}
		
	}
	
}
