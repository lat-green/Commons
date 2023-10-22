package com.greentree.common.graphics.sgl.buffer;
import static org.lwjgl.opengl.GL15.*;

import java.nio.ByteBuffer;

import com.greentree.common.graphics.sgl.enums.gl.GLType;
import org.lwjgl.system.MemoryStack;

public interface IByteBuffer extends IVideoBuffer<ByteBuffer> {

	GLType TYPE = GLType.BYTE;
	@Override
	default GLType getDataType() {
		return TYPE;
	}
	
	@Override
	default void setData(ByteBuffer buffer) {
		glBufferData(getBufferTarget().glEnum, buffer, getUsage().glEnum);
	}

	default void setData(byte[] buffer) {
		try (final var stack = MemoryStack.create(buffer.length * TYPE.size).push()) {
			setData(stack.bytes(buffer));
		}
	}
}
