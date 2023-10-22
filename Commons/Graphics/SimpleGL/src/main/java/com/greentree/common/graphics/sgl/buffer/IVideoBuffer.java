package com.greentree.common.graphics.sgl.buffer;

import static org.lwjgl.opengl.GL15.*;

import java.nio.Buffer;

import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.enums.gl.param.value.GLUsage;
import com.greentree.common.graphics.sgl.enums.gl.target.GLBufferEnumTarget;

public interface IVideoBuffer<B extends Buffer> {

	default void bind() {
		glBindBuffer(getBufferTarget().glEnum, glID());
	}

	GLBufferEnumTarget getBufferTarget();
	GLType getDataType();
	GLUsage getUsage();
	int glID();
	void setData(B buffer);

	int size();

	default void unbind() {
		glBindBuffer(getBufferTarget().glEnum, 0);
	}



}
