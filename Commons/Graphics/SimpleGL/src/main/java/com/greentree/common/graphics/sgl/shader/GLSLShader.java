package com.greentree.common.graphics.sgl.shader;

import static com.greentree.common.graphics.sgl.enums.gl.param.name.GLShaderParam_int_vec.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.InputStream;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.enums.gl.GLShaderType;
import com.greentree.commons.util.InputStreamUtil;

public final class GLSLShader extends GLObject {
	
	public GLSLShader(final InputStream source, final GLShaderType type) {
		this(InputStreamUtil.readString(source), type);
	}
	
	public GLSLShader(final String source, final GLShaderType type) {
		super(glCreateShader(type.glEnum));
		glShaderSource(glID, source);
		
		glCompileShader(glID);
		checkShaderCompileStatus(glID, type);
	}
	
	private static void checkShaderCompileStatus(final int id, GLShaderType type) {
		final int[] errc = {1};
		glGetShaderiv(id, COMPILE_STATUS.glEnum, errc);
		if(0 == errc[0])
			throw new IllegalStateException(
					String.format("Error creating %s shader: %s", type.name(), glGetShaderInfoLog(id)));
	}
	
	public int getId() {
		return this.glID;
	}
	
	@Override
	public String toString() {
		return "GLSLShader [" + glID + "]";
	}
	
	@Override
	public void delete() {
		glDeleteShader(glID);
	}
	
	@Override
	public GLObjectBinder binder() {
		return GLObjectBinder.NULL;
	}
	
}
