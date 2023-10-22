package com.greentree.common.graphics.sgl.enums.gl.target;
import static org.lwjgl.opengl.GL11.*;

public enum GLTexture1DEnumTarget implements GLTextureEnumTarget {

	TEXTURE_1D(GL_TEXTURE_1D),
	;

	public final int glEnum;

	GLTexture1DEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}
	
	public static GLTexture1DEnumTarget get(int glEnum) {
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
