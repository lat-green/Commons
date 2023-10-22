package com.greentree.common.graphics.sgl.texture.gl.cubemap;

import static com.greentree.common.graphics.sgl.enums.gl.GLType.*;
import static com.greentree.common.graphics.sgl.enums.gl.target.GLTexture2DEnumTarget.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLPixelFormat;
import com.greentree.common.graphics.sgl.enums.gl.target.GLTexture2DEnumTarget;
import com.greentree.common.graphics.sgl.texture.WriteTexture2D;

public class PosZCubeMapTexture implements WriteTexture2D {
	public static final GLTexture2DEnumTarget GL_TEXTURE_CUBE_MAP_DIRECTION = TEXTURE_CUBE_MAP_POSITIVE_Z;

	public static final PosZCubeMapTexture INSTANCE = new PosZCubeMapTexture();


	private PosZCubeMapTexture() {
	}

	@Override
	public void setData(GLPixelFormat internalFormat, int width, int height) {
		glTexImage2D(GL_TEXTURE_CUBE_MAP_DIRECTION.glEnum, 0, internalFormat.glEnum, width, height, 0, internalFormat.base().glEnum, UNSIGNED_BYTE.glEnum, 0);
	}

	@Override
	public void setData(GLPixelFormat internalFormat, int width, int height, GLPixelFormat format, ByteBuffer buffer) {
		glTexImage2D(GL_TEXTURE_CUBE_MAP_DIRECTION.glEnum, 0, internalFormat.glEnum, width, height, 0, format.glEnum, UNSIGNED_BYTE.glEnum, buffer);
	}


}
