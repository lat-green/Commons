package com.greentree.common.graphics.sgl.enums.gl.param.name;

import static org.lwjgl.opengl.GL15.*;

public enum GLBufferParam_int {

	BUFFER_SIZE(GL_BUFFER_SIZE),
	;

	public final int glEnum;

	GLBufferParam_int(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLBufferParam_int get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
