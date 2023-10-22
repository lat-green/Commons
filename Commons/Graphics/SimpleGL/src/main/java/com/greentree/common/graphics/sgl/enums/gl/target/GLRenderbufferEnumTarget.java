package com.greentree.common.graphics.sgl.enums.gl.target;

import static org.lwjgl.opengl.GL30.*;

public enum GLRenderbufferEnumTarget implements GLEnumTarget {
	RENDERBUFFER(GL_RENDERBUFFER),
	;

	public final int glEnum;

	GLRenderbufferEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLRenderbufferEnumTarget get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

	@Override
	public int glEnum() {
		return glEnum;
	}
	
}
