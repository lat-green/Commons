package com.greentree.common.graphics.sgl.enums.gl.param.value;

import static org.lwjgl.opengl.GL15.*;

public enum GLUsage {

	STREAM_DRAW(GL_STREAM_DRAW),
	STREAM_READ(GL_STREAM_READ),
	STREAM_COPY(GL_STREAM_COPY),
	STATIC_DRAW(GL_STATIC_DRAW),
	STATIC_READ(GL_STATIC_READ),
	STATIC_COPY(GL_STATIC_COPY),
	DYNAMIC_DRAW(GL_DYNAMIC_DRAW),
	DYNAMIC_READ(GL_DYNAMIC_READ),
	DYNAMIC_COPY(GL_DYNAMIC_COPY),
	;

	public final int glEnum;

	GLUsage(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLUsage get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

}
