package com.greentree.common.graphics.sgl.callback;

import java.util.function.Supplier;

import com.greentree.common.graphics.sgl.SGLFW.ButtonAction;
import com.greentree.common.graphics.sgl.SGLFW.GLFWKey;
import com.greentree.common.graphics.sgl.SGLFW.KeyMode;

@FunctionalInterface
public interface SGLFWKeyCallbackI {

	static SGLFWKeyCallbackI create(Iterable<? extends SGLFWKeyCallbackI> callbacks) {
		return (key, scancode, action, mods)-> {
			for(var c : callbacks)
				c.invoke(key, scancode, action, mods);
		};
	}
	
	void invoke(GLFWKey key, int scancode, ButtonAction action, Supplier<KeyMode[]> mods);

}
