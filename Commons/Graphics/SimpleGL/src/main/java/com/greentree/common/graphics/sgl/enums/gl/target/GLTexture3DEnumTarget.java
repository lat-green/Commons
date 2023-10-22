package com.greentree.common.graphics.sgl.enums.gl.target;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.*;

public enum GLTexture3DEnumTarget implements GLTextureEnumTarget {

	TEXTURE_3D(GL_TEXTURE_3D),
	TEXTURE_2D_ARRAY(GL_TEXTURE_2D_ARRAY),
	;

	public final int glEnum;

	GLTexture3DEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLTexture3DEnumTarget get(int glEnum) {
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
