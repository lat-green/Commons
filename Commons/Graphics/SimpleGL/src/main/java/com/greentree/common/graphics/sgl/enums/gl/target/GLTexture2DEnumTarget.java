package com.greentree.common.graphics.sgl.enums.gl.target;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;

public enum GLTexture2DEnumTarget implements GLTextureEnumTarget {

	TEXTURE_2D(GL_TEXTURE_2D),
	TEXTURE_1D_ARRAY(GL_TEXTURE_1D_ARRAY),
	TEXTURE_CUBE_MAP_POSITIVE_X(GL_TEXTURE_CUBE_MAP_POSITIVE_X),
	TEXTURE_CUBE_MAP_POSITIVE_Y(GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
	TEXTURE_CUBE_MAP_POSITIVE_Z(GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
	TEXTURE_CUBE_MAP_NEGATIVE_X(GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
	TEXTURE_CUBE_MAP_NEGATIVE_Y(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
	TEXTURE_CUBE_MAP_NEGATIVE_Z(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z),
	;

	public final int glEnum;

	GLTexture2DEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLTexture2DEnumTarget get(int glEnum) {
		for(var e : values())
			if(e.glEnum == glEnum)
				return e;
		return null;
	}

	@Override
	public int glEnum() {
		return glEnum;
	}

}
