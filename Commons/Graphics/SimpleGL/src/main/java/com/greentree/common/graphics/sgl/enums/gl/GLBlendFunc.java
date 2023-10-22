package com.greentree.common.graphics.sgl.enums.gl;


import static org.lwjgl.opengl.GL11.*;

public enum GLBlendFunc{
	ZERO(GL_ZERO),
	ONE(GL_ONE),
	SRC_COLOR(GL_SRC_COLOR),
	ONE_MINUS_SRC_COLOR(GL_ONE_MINUS_SRC_COLOR),
	SRC_ALPHA(GL_SRC_ALPHA),
	ONE_MINUS_SRC_ALPHA(GL_ONE_MINUS_SRC_ALPHA),
	DST_ALPHA(GL_DST_ALPHA),
	ONE_MINUS_DST_ALPHA(GL_ONE_MINUS_DST_ALPHA),
	;


	public final int glEnum;

	GLBlendFunc(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLBlendFunc get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

}
