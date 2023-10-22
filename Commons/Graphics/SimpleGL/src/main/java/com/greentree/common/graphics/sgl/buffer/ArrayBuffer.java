package com.greentree.common.graphics.sgl.buffer;

import static com.greentree.common.graphics.sgl.enums.gl.target.GLBufferEnumTarget.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.Buffer;

import com.greentree.common.graphics.sgl.enums.gl.target.GLBufferEnumTarget;
public abstract class ArrayBuffer<T extends Buffer> extends VideoBuffer<T> {

	public static final GLBufferEnumTarget GL_BUFFER_TARGET = ARRAY_BUFFER;

	@Override
	public GLBufferEnumTarget getBufferTarget() {
		return GL_BUFFER_TARGET;
	}

	@Override
	public final GLObjectBinder binder() {
		return BINDER;
	}
	

	public static final Binder BINDER = new Binder();
	
    public static final class Binder extends GLObjectBinder {
    	
    	private Binder() {
    	}
    	
		@Override
		protected void glBind(int glID) {
			glBindBuffer(GL_BUFFER_TARGET.glEnum, glID);
		}
    
    }
	
}
