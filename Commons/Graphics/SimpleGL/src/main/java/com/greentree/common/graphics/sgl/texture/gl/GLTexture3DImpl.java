package com.greentree.common.graphics.sgl.texture.gl;

import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.enums.gl.target.GLTexture3DEnumTarget;

public class GLTexture3DImpl extends GLTexture3D {
	
	public static final Texture3DImplBinder BINDER = new Texture3DImplBinder();
	
	public static final class Texture3DImplBinder extends GLObjectBinder {
		
		private Texture3DImplBinder() {
		}
		
		@Override
		protected void glBind(int glID) {
			glBindTexture(GL_TEXTURE_TARGET.glEnum, glID);
		}
		
	}
	
	public final static GLTexture3DEnumTarget GL_TEXTURE_TARGET = GLTexture3DEnumTarget.TEXTURE_3D;
	
	
	@Override
	public GLTexture3DEnumTarget getTextureTarget() {
		return GL_TEXTURE_TARGET;
	}
	
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}
	
}
