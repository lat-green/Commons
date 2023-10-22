package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL40.*;

public enum GLSLType{

	FLOAT(GL_FLOAT),
	FLOAT_VEC2(GL_FLOAT_VEC2),
	FLOAT_VEC3(GL_FLOAT_VEC3),
	FLOAT_VEC4(GL_FLOAT_VEC4),

	INT(GL_INT),
	INT_VEC2(GL_INT_VEC2),
	INT_VEC3(GL_INT_VEC3),
	INT_VEC4(GL_INT_VEC4),

	UNSIGNED_INT(GL_UNSIGNED_INT),
	DOUBLE(GL_DOUBLE),
	DOUBLE_VEC2(GL_DOUBLE_VEC2),
	DOUBLE_VEC3(GL_DOUBLE_VEC3),
	DOUBLE_VEC4(GL_DOUBLE_VEC4),
	FLOAT_MAT2(GL_FLOAT_MAT2),
	FLOAT_MAT3(GL_FLOAT_MAT3),
	FLOAT_MAT4(GL_FLOAT_MAT4),
	SAMPLER_2D(GL_SAMPLER_2D),
	SAMPLER_CUBE(GL_SAMPLER_CUBE),
	;
	
	public final int glEnum;
	
	GLSLType(int glEnum) {
		this.glEnum = glEnum;
	}
	
	public static GLSLType get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		System.out.println(glEnum);
		return null;
	}
	
}
