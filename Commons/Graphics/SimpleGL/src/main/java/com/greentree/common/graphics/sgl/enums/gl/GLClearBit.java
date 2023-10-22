package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL11.*;

public enum GLClearBit{
	COLOR_BUFFER_BIT(GL_COLOR_BUFFER_BIT),
	DEPTH_BUFFER_BIT(GL_DEPTH_BUFFER_BIT),
	ACCUM_BUFFER_BIT(GL_ACCUM_BUFFER_BIT),
	STENCIL_BUFFER_BIT(GL_STENCIL_BUFFER_BIT),
	;

	public final int glEnum;

	GLClearBit(int glEnum) {
		this.glEnum = glEnum;
	}

	public static int gl(GLClearBit...bits) {
		var res = 0;
		for(var bit : bits)
			res |= bit.glEnum;
		return res;
	}

}
