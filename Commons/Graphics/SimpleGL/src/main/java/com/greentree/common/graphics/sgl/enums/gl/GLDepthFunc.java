package com.greentree.common.graphics.sgl.enums.gl;


import static org.lwjgl.opengl.GL11.*;

public enum GLDepthFunc{

	NEVER(GL_NEVER),
	LESS(GL_LESS),
	EQUAL(GL_EQUAL),
	LEQUAL(GL_LEQUAL),
	GREATER(GL_GREATER),
	NOTEQUAL(GL_NOTEQUAL),
	GEQUAL(GL_GEQUAL),
	ALWAYS(GL_ALWAYS),
	;


	public final int glEnum;

	GLDepthFunc(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLDepthFunc get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

}
