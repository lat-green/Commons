package com.greentree.common.graphics.sgl.enums.gl.param.value;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;

/**
 * @author Arseny Latyshev
 *
 */
public enum GLWrapping {
	REPEAT(GL_REPEAT),
	MIRRORED_REPEAT(GL_MIRRORED_REPEAT),
	CLAMP_TO_EDGE(GL_CLAMP_TO_EDGE),
	CLAMP_TO_BORDER(GL_CLAMP_TO_BORDER),
	;

	public final int glEnum;

	GLWrapping(int glEnum) {
		this.glEnum = glEnum;
	}

}
