package com.greentree.common.graphics.sgl.buffer;

import static org.lwjgl.opengl.GL15.*;

import java.nio.IntBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLType;
import org.lwjgl.system.MemoryStack;

public interface IIntBuffer extends IVideoBuffer<IntBuffer> {

	GLType TYPE = GLType.INT;

	@Override
	default GLType getDataType() {
		return TYPE;
	}
	@Override
	default void setData(IntBuffer buffer) {
		glBufferData(getBufferTarget().glEnum, buffer, getUsage().glEnum);
	}

	default void setData(int[] buffer) {
		try (final var stack = MemoryStack.create(buffer.length * TYPE.size).push()) {
			setData(stack.ints(buffer));
		}
	}
	
}
