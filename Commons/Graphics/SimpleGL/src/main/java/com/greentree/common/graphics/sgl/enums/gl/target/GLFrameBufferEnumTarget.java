package com.greentree.common.graphics.sgl.enums.gl.target;

import static org.lwjgl.opengl.GL30.*;

public enum GLFrameBufferEnumTarget implements GLEnumTarget {
	FRAMEBUFFER(GL_FRAMEBUFFER),
	READ_FRAMEBUFFER(GL_READ_FRAMEBUFFER),
	DRAW_FRAMEBUFFER(GL_DRAW_FRAMEBUFFER),
	;

	public final int glEnum;

	GLFrameBufferEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLFrameBufferEnumTarget get(int glEnum) {
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
