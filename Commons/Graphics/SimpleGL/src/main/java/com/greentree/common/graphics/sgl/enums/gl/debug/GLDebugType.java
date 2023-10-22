package com.greentree.common.graphics.sgl.enums.gl.debug;

import static org.lwjgl.opengl.GL43.*;

public enum GLDebugType{

	DEBUG_TYPE_ERROR(GL_DEBUG_TYPE_ERROR, "Error"),
	DEBUG_TYPE_DEPRECATED_BEHAVIOR(GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR, "Deprecated Behaviour"),
	DEBUG_TYPE_UNDEFINED_BEHAVIOR(GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR, "Undefined Behaviour"),
	DEBUG_TYPE_PORTABILITY(GL_DEBUG_TYPE_PORTABILITY, "Portability"),
	DEBUG_TYPE_PERFORMANCE(GL_DEBUG_TYPE_PERFORMANCE, "Performance"),
	DEBUG_TYPE_MARKER(GL_DEBUG_TYPE_MARKER, "Marker"),
	DEBUG_TYPE_PUSH_GROUP(GL_DEBUG_TYPE_PUSH_GROUP, "Push Group"),
	DEBUG_TYPE_POP_GROUP(GL_DEBUG_TYPE_POP_GROUP, "Pop Group"),
	DEBUG_TYPE_OTHER(GL_DEBUG_TYPE_OTHER, "Other"),
	;

	public final int glEnum;
	public final String name;

	GLDebugType(int glEnum, String name) {
		this.glEnum = glEnum;
		this.name = name;
	}

	public static GLDebugType get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
