package com.greentree.common.graphics.sgl.freambuffer;

import static com.greentree.common.graphics.sgl.enums.gl.GLFramebufferStatus.*;
import static com.greentree.common.graphics.sgl.enums.gl.target.GLFrameBufferEnumTarget.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.greentree.common.graphics.sgl.enums.gl.GLAttachment;
import com.greentree.common.graphics.sgl.enums.gl.GLDrawBufferMode;
import com.greentree.common.graphics.sgl.enums.gl.GLFramebufferStatus;
import com.greentree.common.graphics.sgl.freambuffer.attachment.Attachment;
import com.greentree.commons.util.exception.MultiException;

public class AttachmentFreamBuffer extends FreamBuffer {
	
	private final List<Attachment> colors = new ArrayList<>();
	private final Attachment depth;
	
	public AttachmentFreamBuffer(Collection<? extends Attachment> colors, Attachment depth, int width, int height) {
		this.depth = depth;
		this.colors.addAll(colors);
		
		BINDER.bind(glID);
		
		final int size = colors.size();
		
		if(size == 0) {
			glDrawBuffer(GLDrawBufferMode.NONE.glEnum);
			glReadBuffer(GLDrawBufferMode.NONE.glEnum);
		}else {
			int i = 0;
			for(var c : colors)
				c.bind(GLAttachment.color(i++));
			glDrawBuffers(get_COLOR_ATTACHMENTS(size));
		}
		
		if(depth != null)
			depth.bind(GLAttachment.DEPTH);
		
		resize(width, height);
		
		var error = GLFramebufferStatus.get(glCheckFramebufferStatus(FRAMEBUFFER.glEnum));
		if(error != FRAMEBUFFER_COMPLETE)
			throw new MultiException("Framebuffer not complete! " + error + " " + colors + " " + depth);
		
		BINDER.unbind();
	}
	
	private static int[] get_COLOR_ATTACHMENTS(int size) {
		final var COLOR_ATTACHMENTS = new int[size];
		for(int i = 0; i < size; i++)
			COLOR_ATTACHMENTS[i] = GLAttachment.color(i).glEnum;
		return COLOR_ATTACHMENTS;
	}
	
	@Override
	public int size() {
		return colors.size();
	}
	
	@Override
	public Attachment getColor(int i) {
		return colors.get(i);
	}
	
	@Override
	public Attachment getDepth() {
		return depth;
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		for(var a : colors)
			a.resize(getWidth(), getHeight());
		if(depth != null)
			depth.resize(getWidth(), getHeight());
	}
	
}
