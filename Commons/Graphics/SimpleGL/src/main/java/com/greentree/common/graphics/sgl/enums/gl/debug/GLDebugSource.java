package com.greentree.common.graphics.sgl.enums.gl.debug;

import static org.lwjgl.opengl.GL43.*;

public enum GLDebugSource {

	DEBUG_SOURCE_API(GL_DEBUG_SOURCE_API, "API"),
	DEBUG_SOURCE_WINDOW_SYSTEM(GL_DEBUG_SOURCE_WINDOW_SYSTEM, "Window System"),
	DEBUG_SOURCE_SHADER_COMPILER(GL_DEBUG_SOURCE_SHADER_COMPILER, "Shader Compiler"),
	DEBUG_SOURCE_THIRD_PARTY(GL_DEBUG_SOURCE_THIRD_PARTY, "Third Party"),
	DEBUG_SOURCE_APPLICATION(GL_DEBUG_SOURCE_APPLICATION, "Application"),
	DEBUG_SOURCE_OTHER(GL_DEBUG_SOURCE_OTHER, "Other"),
	;

	public final int glEnum;
	public final String name;

	GLDebugSource(int glEnum, String name) {
		this.glEnum = glEnum;
		this.name = name;
	}

	public static GLDebugSource get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}



}
