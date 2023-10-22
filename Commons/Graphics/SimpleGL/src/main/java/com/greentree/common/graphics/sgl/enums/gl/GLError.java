package com.greentree.common.graphics.sgl.enums.gl;


import static org.lwjgl.opengl.GL11.*;

public enum GLError {

	NO_ERROR(GL_NO_ERROR),
	INVALID_ENUM(GL_INVALID_ENUM),
	INVALID_VALUE(GL_INVALID_VALUE),
	INVALID_OPERATION(GL_INVALID_OPERATION),
	STACK_OVERFLOW(GL_STACK_OVERFLOW),
	STACK_UNDERFLOW(GL_STACK_UNDERFLOW),
	OUT_OF_MEMORY(GL_OUT_OF_MEMORY),;

	public final int glEnum;

	GLError(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLError get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

}
