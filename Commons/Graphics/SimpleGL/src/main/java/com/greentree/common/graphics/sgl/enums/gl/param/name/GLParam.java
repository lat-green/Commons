package com.greentree.common.graphics.sgl.enums.gl.param.name;


import static org.lwjgl.opengl.GL11.*;

public enum GLParam {
	PROJECTION_MATRIX(GL_PROJECTION_MATRIX),
	MODELVIEW_MATRIX(GL_MODELVIEW_MATRIX),
	;

	public final int glEnum;

	GLParam(int i) {
		glEnum = i;
	}

}
