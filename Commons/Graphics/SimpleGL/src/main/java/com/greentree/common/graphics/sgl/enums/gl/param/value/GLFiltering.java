package com.greentree.common.graphics.sgl.enums.gl.param.value;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Arseny Latyshev
 *
 */
public enum GLFiltering {
	NEAREST(GL_NEAREST),
	LINEAR(GL_LINEAR),
	NEAREST_MIPMAP_NEAREST(GL_NEAREST_MIPMAP_NEAREST),
	LINEAR_MIPMAP_NEAREST(GL_LINEAR_MIPMAP_NEAREST),
	NEAREST_MIPMAP_LINEAR(GL_NEAREST_MIPMAP_LINEAR),
	LINEAR_MIPMAP_LINEAR(GL_LINEAR_MIPMAP_LINEAR),
	;

	public final int glEnum;

	GLFiltering(int glEnum) {
		this.glEnum = glEnum;
	}
}
