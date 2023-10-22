package com.greentree.common.graphics.sgl.enums.gl.param.name;


import static org.lwjgl.opengl.GL20.*;

public enum GLShaderParam_int_vec {
	SHADER_TYPE(GL_SHADER_TYPE),
	DELETE_STATUS(GL_DELETE_STATUS),
	COMPILE_STATUS(GL_COMPILE_STATUS),
	LINK_STATUS(GL_LINK_STATUS),
	VALIDATE_STATUS(GL_VALIDATE_STATUS),
	INFO_LOG_LENGTH(GL_INFO_LOG_LENGTH),
	ATTACHED_SHADERS(GL_ATTACHED_SHADERS),
	ACTIVE_UNIFORMS(GL_ACTIVE_UNIFORMS),
	ACTIVE_UNIFORM_MAX_LENGTH(GL_ACTIVE_UNIFORM_MAX_LENGTH),
	ACTIVE_ATTRIBUTES(GL_ACTIVE_ATTRIBUTES),
	ACTIVE_ATTRIBUTE_MAX_LENGTH(GL_ACTIVE_ATTRIBUTE_MAX_LENGTH),
	SHADER_SOURCE_LENGTH(GL_SHADER_SOURCE_LENGTH),
	;

	public final int glEnum;

	GLShaderParam_int_vec(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLShaderParam_int_vec get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}
}
