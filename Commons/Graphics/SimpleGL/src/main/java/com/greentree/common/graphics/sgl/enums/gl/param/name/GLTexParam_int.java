package com.greentree.common.graphics.sgl.enums.gl.param.name;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

public enum GLTexParam_int {

	TEXTURE_MAG_FILTER(GL_TEXTURE_MAG_FILTER),
	TEXTURE_MIN_FILTER(GL_TEXTURE_MIN_FILTER),
	TEXTURE_WRAP_S(GL_TEXTURE_WRAP_S),
	TEXTURE_WRAP_T(GL_TEXTURE_WRAP_T),
	TEXTURE_WRAP_R(GL_TEXTURE_WRAP_R),
	;

	public final int glEnum;

	GLTexParam_int(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLTexParam_int get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
