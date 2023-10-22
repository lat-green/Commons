package com.greentree.common.graphics.sgl.freambuffer;

import static com.greentree.common.graphics.sgl.enums.gl.target.GLFrameBufferEnumTarget.*;
import static org.lwjgl.opengl.GL30.*;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.enums.gl.GLClearBit;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLFiltering;
import com.greentree.common.graphics.sgl.freambuffer.attachment.Attachment;
import com.greentree.common.graphics.sgl.freambuffer.attachment.TextureAttachment;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture;


public abstract class FreamBuffer extends GLObject {
	
	public static final Binder BINDER = new Binder();
	
	
	private int width, height;
	
	public FreamBuffer() {
		super(glGenFramebuffers());
	}
	
	public static void copyTo(FreamBuffer fromFB, FreamBuffer toFB, GLClearBit[] mask, GLFiltering filtering,
			Dimention from, Dimention to) {
		glBindFramebuffer(READ_FRAMEBUFFER.glEnum, getID(fromFB));
		glBindFramebuffer(DRAW_FRAMEBUFFER.glEnum, getID(toFB));
		
		glBlitFramebuffer(from.x1, from.y1, from.x2, from.y2, to.x1, to.y1, to.x2, to.y2, GLClearBit.gl(mask),
				filtering.glEnum);
		
		glBindFramebuffer(READ_FRAMEBUFFER.glEnum, 0);
		glBindFramebuffer(DRAW_FRAMEBUFFER.glEnum, 0);
	}
	
	@Deprecated
	public static void unbinds() {
		BINDER.unbind();
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}
	
	@Override
	public void delete() {
		glDeleteFramebuffers(glID);
	}
	
	public abstract Attachment getColor(int i);
	
	public GLTexture getColorTexture(int i) {
		final var attach = getColor(i);
		if(attach == null)
			return null;
		if(attach instanceof TextureAttachment)
			return ((TextureAttachment<?>) attach).getTexture();
		return null;
	}
	
	public abstract Attachment getDepth();
	
	public GLTexture getDepthTexture() {
		final var attach = getDepth();
		if(attach == null)
			return null;
		if(attach instanceof TextureAttachment)
			return ((TextureAttachment<?>) attach).getTexture();
		return null;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public final void readDepthFrom(FreamBuffer from) {
		var fromd = new Dimention(from);
		var d = new Dimention(this);
		copyTo(from, this, new GLClearBit[]{GLClearBit.DEPTH_BUFFER_BIT}, GLFiltering.NEAREST, fromd, d);
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public abstract int size();
	
	public final void writeDepthTo(FreamBuffer to) {
		var d = new Dimention(this);
		var fto = new Dimention(to);
		copyTo(this, to, new GLClearBit[]{GLClearBit.DEPTH_BUFFER_BIT}, GLFiltering.NEAREST, d, fto);
	}
	
	public static final class Binder extends GLObjectBinder {
		
		private Binder() {
		}
		
		@Override
		protected void glBind(int glID) {
			glBindFramebuffer(FRAMEBUFFER.glEnum, glID);
		}
		
	}
	
	public static final class Dimention {
		
		private final int x1, y1, x2, y2;
		
		public Dimention(FreamBuffer fb) {
			this(fb.getWidth(), fb.getHeight());
		}
		
		public Dimention(int width, int height) {
			this(0, 0, width, height);
		}
		
		public Dimention(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		@Override
		public String toString() {
			return "Dimention [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2 + "]";
		}
	}
}
