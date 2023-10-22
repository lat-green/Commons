package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL11.*;

public enum GLPolygonMode {

	POINT(GL_POINT),
	LINE(GL_LINE),
	FILL(GL_FILL),
	;
	public final int glEnum;

	GLPolygonMode(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLPolygonMode get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
