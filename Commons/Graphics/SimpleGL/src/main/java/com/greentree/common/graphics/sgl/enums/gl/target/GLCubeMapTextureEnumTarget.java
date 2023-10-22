package com.greentree.common.graphics.sgl.enums.gl.target;
import static org.lwjgl.opengl.GL13.*;

public enum GLCubeMapTextureEnumTarget implements GLTextureEnumTarget {

	TEXTURE_CUBE_MAP(GL_TEXTURE_CUBE_MAP),
	;

	public final int glEnum;

	GLCubeMapTextureEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLCubeMapTextureEnumTarget get(int glEnum) {
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
