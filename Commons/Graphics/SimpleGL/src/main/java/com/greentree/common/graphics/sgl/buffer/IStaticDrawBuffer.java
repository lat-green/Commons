package com.greentree.common.graphics.sgl.buffer;

import static com.greentree.common.graphics.sgl.enums.gl.param.value.GLUsage.*;

import java.nio.Buffer;

import com.greentree.common.graphics.sgl.enums.gl.param.value.GLUsage;

public interface IStaticDrawBuffer<T extends Buffer> extends IVideoBuffer<T> {

	@Override
	default GLUsage getUsage() {
		return STATIC_DRAW;
	}
	
}
