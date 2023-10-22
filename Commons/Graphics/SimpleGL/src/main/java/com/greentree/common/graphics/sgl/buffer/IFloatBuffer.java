package com.greentree.common.graphics.sgl.buffer;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLType;
import org.lwjgl.system.MemoryStack;

public interface IFloatBuffer extends IVideoBuffer<FloatBuffer> {

	GLType TYPE = GLType.FLOAT;

	@Override
	default GLType getDataType() {
		return TYPE;
	}

	@Override
	default void setData(FloatBuffer buffer) {
		glBufferData(getBufferTarget().glEnum, buffer, getUsage().glEnum);
	}

	default void setData(float[] buffer) {
		try (final var stack = MemoryStack.create(buffer.length * TYPE.size).push()) {
			setData(stack.floats(buffer));
		}
	}
}
