package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL11.*;

/** @author Arseny Latyshev */
public enum GLPrimitive{

	TRIANGLES(GL_TRIANGLES),
	LINES(GL_LINES),
	LINE_LOOP(GL_LINE_LOOP),
	LINE_STRIP(GL_LINE_STRIP),
	TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
	TRIANGLE_FAN(GL_TRIANGLE_FAN),
	POINTS(GL_POINTS),
	QUADS(GL_QUADS),
	QUAD_STRIP(GL_QUAD_STRIP),
	POLYGON(GL_POLYGON);

	public final int glEnum;

	GLPrimitive(int i) {
		glEnum = i;
	}

	@Deprecated
	public int glEnum(){
		return glEnum;
	}

}
