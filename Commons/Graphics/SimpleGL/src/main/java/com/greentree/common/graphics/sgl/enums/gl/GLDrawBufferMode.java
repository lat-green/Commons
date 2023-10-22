package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL11.*;

public enum GLDrawBufferMode {
	NONE(GL_NONE),
	FRONT_LEFT(GL_FRONT_LEFT),
	FRONT_RIGHT(GL_FRONT_RIGHT),
	BACK_LEFT(GL_BACK_LEFT),
	BACK_RIGHT(GL_BACK_RIGHT),
	FRONT(GL_FRONT),
	BACK(GL_BACK),
	LEFT(GL_LEFT),
	RIGHT(GL_RIGHT),
	FRONT_AND_BACK(GL_FRONT_AND_BACK),
	AUX0(GL_AUX0),
	AUX1(GL_AUX1),
	AUX2(GL_AUX2),
	AUX3(GL_AUX3),
	;
	public final int glEnum;

	GLDrawBufferMode(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLDrawBufferMode get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
