package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL11.*;

public enum GLClientState {
	VERTEX_ARRAY(GL_VERTEX_ARRAY), COLOR_ARRAY(GL_COLOR_ARRAY), TEXTURE_COORD_ARRAY(GL_TEXTURE_COORD_ARRAY);

	public final int glEnum;

	GLClientState(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLClientState get(int glEunm) {
		for(var cs : values())
			if(cs.glEnum == glEunm)
				return cs;
		return null;
	}

}
