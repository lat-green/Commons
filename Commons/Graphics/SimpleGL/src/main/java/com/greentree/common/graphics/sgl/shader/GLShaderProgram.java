package com.greentree.common.graphics.sgl.shader;

import static org.lwjgl.opengl.GL20.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.enums.gl.GLError;
import com.greentree.common.graphics.sgl.enums.gl.param.name.GLShaderParam_int_vec;

public final class GLShaderProgram extends GLObject {
	
	public static final GLShaderProgramBinder BINDER = new GLShaderProgramBinder();
	
	public GLShaderProgram(Iterable<GLSLShader> shaders) {
		super(glCreateProgram());
		for(var shader : shaders)
			glAttachShader(glID, shader.getId());
		glLinkProgram(glID);
		validateParam(GLShaderParam_int_vec.LINK_STATUS, "Shader program link error:");
		glValidateProgram(glID);
		validateParam(GLShaderParam_int_vec.VALIDATE_STATUS, "Shader program validate error:");
		for(var shader : shaders)
			glDetachShader(glID, shader.getId());
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}
	
	@Override
	public void delete() {
		glDeleteProgram(glID);
	}
	
	public GLUniformLocation getUL(final String name) {
		return new GLUniformLocation(getUniformLocation(name));
	}
	
	public int getUniformLocation(final String name) {
		return glGetUniformLocation(glID, name);
	}
	
	public Iterable<? extends String> getUniformLocationNames() {
		final var result = new ArrayList<String>();
		final var length = new int[1];
		glGetProgramiv(glID, GL_ACTIVE_UNIFORMS, length);
		final var ACTIVE_UNIFORMS = length[0];
		glGetProgramiv(glID, GL_ACTIVE_UNIFORM_MAX_LENGTH, length);
		try(final var stack = MemoryStack.create(length[0]).push()) {
			final var name = stack.malloc(length[0]);
			final var NULL = new int[1];
			for(var i = 0; i < ACTIVE_UNIFORMS; i++) {
				name.position(0);
				glGetActiveUniform(glID, i, length, NULL, NULL, name);
				final var l = name.limit();
				name.limit(length[0]);
				final var n = StandardCharsets.UTF_8.decode(name).toString();
				name.limit(l);
				result.add(n);
			}
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(glID);
	}
	
	
	@Override
	public String toString() {
		return "GLShaderProgram [" + glID + "]";
	}
	
	
	@Deprecated
	private void validateParam(final GLShaderParam_int_vec pname, final String errFormat) {
		final var errc = new int[1];
		glGetProgramiv(glID, pname.glEnum, errc);
		if(GLError.NO_ERROR.glEnum == errc[0]) {
			glGetProgramiv(glID, GLShaderParam_int_vec.INFO_LOG_LENGTH.glEnum, errc);
			var s = glGetProgramInfoLog(glID, errc[0]);
			System.err.printf("%s %s\n", errFormat, s);
		}
	}
	
	public static final class GLShaderProgramBinder extends GLObjectBinder {
		
		private GLShaderProgramBinder() {
		}
		
		@Override
		protected void glBind(int glID) {
			glUseProgram(glID);
		}
		
	}
	
}
