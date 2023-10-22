package com.greentree.common.graphics.sgl.enums.gl;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL43.*;

/** @author Arseny Latyshev Supported OpenGL 4.3+ shader types */
public enum GLShaderType {

	COMPUTE(GL_COMPUTE_SHADER),
	FRAGMENT(GL_FRAGMENT_SHADER),
	GEOMETRY(GL_GEOMETRY_SHADER),
	TESS_CONTROL(GL_TESS_CONTROL_SHADER),
	TESS_EVALUATION(GL_TESS_EVALUATION_SHADER),
	VERTEX(GL_VERTEX_SHADER);

	public final int glEnum;

	GLShaderType(int glEnum) {
		this.glEnum = glEnum;
	}



}
