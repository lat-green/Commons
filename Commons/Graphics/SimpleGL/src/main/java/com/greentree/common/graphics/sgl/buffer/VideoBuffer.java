package com.greentree.common.graphics.sgl.buffer;

import static org.lwjgl.opengl.GL15.*;

import java.nio.Buffer;

import com.greentree.common.graphics.sgl.GLObject;
import com.greentree.common.graphics.sgl.enums.gl.param.name.GLBufferParam_int;


/** @author Viktor Gubin */
public abstract class VideoBuffer<T extends Buffer> extends GLObject implements IVideoBuffer<T> {
	
	public VideoBuffer() {
		super(glGenBuffers());
		bind();
		unbind();
	}
	
	@Override
	public int glID() {
		return super.glID();
	}
	
	@Override
	public void delete() {
		glDeleteBuffers(glID());
	}
	
	@Override
	public int size() {
		return glGetBufferParameteri(getBufferTarget().glEnum, GLBufferParam_int.BUFFER_SIZE.glEnum)
				/ getDataType().size;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + glID() + "]";
	}
	
}
