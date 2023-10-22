package com.greentree.common.graphics.sgl.texture.gl.cubemap;


import static com.greentree.common.graphics.sgl.enums.gl.target.GLCubeMapTextureEnumTarget.*;
import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.enums.gl.target.GLCubeMapTextureEnumTarget;
import com.greentree.common.graphics.sgl.texture.WriteTexture2D;
import com.greentree.common.graphics.sgl.texture.gl.GLTexture3D;
import com.greentree.commons.util.iterator.IteratorUtil;

public class GLCubeMapTexture extends GLTexture3D {

	public final static GLCubeMapTextureEnumTarget GL_TEXTURE_TARGET = TEXTURE_CUBE_MAP;

	public static final CubeMapTextureBinder BINDER = new CubeMapTextureBinder();

	public GLCubeMapTexture() {
	}

	@Deprecated
	public static void unbinds() {
		BINDER.unbind();
	}
	
	public Iterable<WriteTexture2D> dirs() {
		return IteratorUtil.iterable(
				PosXCubeMapTexture.INSTANCE,
				NegXCubeMapTexture.INSTANCE,
				PosYCubeMapTexture.INSTANCE,
				NegYCubeMapTexture.INSTANCE,
				PosZCubeMapTexture.INSTANCE,
				NegZCubeMapTexture.INSTANCE
		);
	}
	
	@Override
	public GLCubeMapTextureEnumTarget getTextureTarget() {
		return GL_TEXTURE_TARGET;
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}

	@Override
	public String toString() {
		return "CubeMapTexture [" + glID + "]";
	}

	public static final class CubeMapTextureBinder extends GLObjectBinder {

		private CubeMapTextureBinder() {
		}

		@Override
		protected void glBind(int glID) {
			glBindTexture(GL_TEXTURE_TARGET.glEnum, glID);
		}

	}
	
}
