package com.greentree.common.graphics.sgl.enums.gl.target;


import static org.lwjgl.opengl.GL15.*;

public enum GLBufferEnumTarget implements GLEnumTarget {

	ARRAY_BUFFER(GL_ARRAY_BUFFER),
	ELEMENT_ARRAY_BUFFER(GL_ELEMENT_ARRAY_BUFFER),
	;

	public final int glEnum;

	GLBufferEnumTarget(int glEnum) {
		this.glEnum = glEnum;
	}

	public static GLBufferEnumTarget get(int glEnum) {
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
