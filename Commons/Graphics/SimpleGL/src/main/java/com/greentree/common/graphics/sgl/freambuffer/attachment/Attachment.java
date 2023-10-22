package com.greentree.common.graphics.sgl.freambuffer.attachment;

import com.greentree.common.graphics.sgl.enums.gl.GLAttachment;
import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;

public interface Attachment {
	
	GLPixelFormat getInternalformat();
	
	void resize(int width, int height);
	void bind(GLAttachment GL_ATTACHMENT);
	
}
