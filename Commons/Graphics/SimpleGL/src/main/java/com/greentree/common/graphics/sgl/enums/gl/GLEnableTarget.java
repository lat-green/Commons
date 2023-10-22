package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL43.*;

public enum GLEnableTarget {
	BLEND(GL_BLEND),
	CULL_FACE(GL_CULL_FACE),
	DEPTH_TEST(GL_DEPTH_TEST),
	DEBUG_OUTPUT_SYNCHRONOUS(GL_DEBUG_OUTPUT_SYNCHRONOUS),
	DEBUG_OUTPUT(GL_DEBUG_OUTPUT),
	MULTISAMPLE(GL_MULTISAMPLE),
	TEXTURE_2D(GL_TEXTURE_2D),
	;

	public final int glEnum;

	GLEnableTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLEnableTarget get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

}
