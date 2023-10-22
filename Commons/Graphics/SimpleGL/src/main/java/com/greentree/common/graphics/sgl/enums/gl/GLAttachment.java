package com.greentree.common.graphics.sgl.enums.gl;


import static org.lwjgl.opengl.GL30.*;

public enum GLAttachment {
	COLOR0(GL_COLOR_ATTACHMENT0),
	COLOR1(GL_COLOR_ATTACHMENT1),
	COLOR2(GL_COLOR_ATTACHMENT2),
	COLOR3(GL_COLOR_ATTACHMENT3),
	COLOR4(GL_COLOR_ATTACHMENT4),
	COLOR5(GL_COLOR_ATTACHMENT5),
	COLOR6(GL_COLOR_ATTACHMENT6),
	COLOR7(GL_COLOR_ATTACHMENT7),
	COLOR8(GL_COLOR_ATTACHMENT8),
	COLOR9(GL_COLOR_ATTACHMENT9),
	DEPTH(GL_DEPTH_ATTACHMENT),
	STENCIL(GL_STENCIL_ATTACHMENT);

	private static final GLAttachment[] COLOR = new GLAttachment[] {
			COLOR0,
			COLOR1,
			COLOR2,
			COLOR3,
			COLOR4,
			COLOR5,
			COLOR6,
			COLOR7,
			COLOR8,
			COLOR9,
	};
	public final int glEnum;

	GLAttachment(int glEnum) {
		this.glEnum = glEnum;
	}

	public static int[] array(GLAttachment...attachments) {
		final int[] res = new int[attachments.length];
		for(int i = 0; i < res.length; i++) res[i] = attachments[i].glEnum;
		return res;
	}

	public static GLAttachment color(int i) {
		return COLOR[i];
	}

	public static GLAttachment get(int glEnum) {
		for(var a : values())
			if(a.glEnum == glEnum)
				return a;
		return null;
	}
}
