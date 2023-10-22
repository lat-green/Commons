package com.greentree.common.graphics.sgl.texture.gl;

import static com.greentree.common.graphics.sgl.enums.gl.target.GLTexture1DEnumTarget.*;
import static org.lwjgl.opengl.GL11.*;

import com.greentree.common.graphics.sgl.enums.gl.target.GLTexture1DEnumTarget;
import com.greentree.common.graphics.sgl.enums.gl.target.GLTextureEnumTarget;

public class GLTexture1DImpl extends GLTexture1D {

	public final static GLTexture1DEnumTarget GL_TEXTURE_TARGET = TEXTURE_1D;

	public static final Texture1DImplBinder BINDER = new Texture1DImplBinder();
	
    public static final class Texture1DImplBinder extends GLObjectBinder {
    	
    	private Texture1DImplBinder() {
    	}

		@Override
		protected void glBind(int glID) {
			glBindTexture(GL_TEXTURE_TARGET.glEnum, glID);
		}
    
    }
	
    @Override
    public GLTextureEnumTarget getTextureTarget() {
    	return GL_TEXTURE_TARGET;
    }
    
	@Override
	public GLObjectBinder binder() {
		return BINDER;
	}

}
