package com.greentree.common.graphics.sgl.freambuffer;

import java.util.ArrayList;
import java.util.List;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.freambuffer.attachment.Attachment;
import com.greentree.common.graphics.sgl.freambuffer.attachment.CubeMapTextureAttachment;
import com.greentree.common.graphics.sgl.freambuffer.attachment.RenderbuffersAttachment;
import com.greentree.common.graphics.sgl.freambuffer.attachment.Texture2DAttachment;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture2DImpl;
import com.greentree.common.graphics.sgl.texture.gl.cubemap.GLCubeMapTexture;

public class FreamBufferBuilder {
	
	private final List<Attachment> color = new ArrayList<>();
	private Attachment depth;
	
	public FreamBufferBuilder() {
	}
	
	public FreamBufferBuilder addColor(GLTexture2DImpl texture, GLPixelFormat format) {
		return addColor(new Texture2DAttachment(texture, format));
	}
	
	public FreamBufferBuilder addColor2D(GLPixelFormat format) {
		return addColor(new GLTexture2DImpl(), format);
	}
	
	public FreamBufferBuilder addColor(GLCubeMapTexture texture, GLPixelFormat format) {
		return addColor(new CubeMapTextureAttachment(texture, format));
	}
	
	public FreamBufferBuilder addColor(Attachment attachment) {
		color.add(attachment);
		return this;
	}
	
	public FreamBufferBuilder addDepth() {
		if(depth != null)
			throw new UnsupportedOperationException("depth already added");
		depth = new RenderbuffersAttachment(GLPixelFormat.DEPTH_COMPONENT);
		return this;
	}
	
	public FreamBufferBuilder addDepth(GLTexture2DImpl texture) {
		if(depth != null)
			throw new UnsupportedOperationException("depth already added");
		depth = new Texture2DAttachment(texture, GLPixelFormat.DEPTH_COMPONENT);
		return this;
	}
	
	public FreamBufferBuilder addDepth(GLCubeMapTexture texture) {
		if(depth != null)
			throw new UnsupportedOperationException("depth already added");
		depth = new CubeMapTextureAttachment(texture, GLPixelFormat.DEPTH_COMPONENT);
		return this;
	}
	
	public FreamBufferBuilder addDepth(Attachment attachment) {
		if(depth != null)
			throw new UnsupportedOperationException("depth already added");
		depth = attachment;
		return this;
	}
	
	public AttachmentFreamBuffer build(int width, int height) {
		return new AttachmentFreamBuffer(color, depth, width, height);
	}
	
}
