package com.greentree.common.graphics.sgl.enums.gl.debug;

import static org.lwjgl.opengl.GL43.*;

public enum GLDebugSeverity{

	DEBUG_SEVERITY_HIGH(GL_DEBUG_SEVERITY_HIGH, "high"),
	DEBUG_SEVERITY_MEDIUM(GL_DEBUG_SEVERITY_MEDIUM, "medium"),
	DEBUG_SEVERITY_LOW(GL_DEBUG_SEVERITY_LOW, "low"),
	DEBUG_SEVERITY_NOTIFICATION(GL_DEBUG_SEVERITY_NOTIFICATION, "notification"),
	;

	public final int glEnum;
	public final String name;

	GLDebugSeverity(int glEnum, String name) {
		this.glEnum = glEnum;
		this.name = name;
	}

	public static GLDebugSeverity get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
