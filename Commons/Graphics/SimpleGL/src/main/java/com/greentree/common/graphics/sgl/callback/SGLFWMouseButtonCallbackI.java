package com.greentree.common.graphics.sgl.callback;

import java.util.function.Supplier;

import com.greentree.common.graphics.sgl.SGLFW.ButtonAction;
import com.greentree.common.graphics.sgl.SGLFW.GLFWMouseButton;
import com.greentree.common.graphics.sgl.SGLFW.KeyMode;

@FunctionalInterface
public interface SGLFWMouseButtonCallbackI {

	static SGLFWMouseButtonCallbackI create(Iterable<? extends SGLFWMouseButtonCallbackI> callbacks) {
		return (button, action, mods)-> {
			for(var c : callbacks)
				c.invoke(button, action, mods);
		};
	}
	
	void invoke(GLFWMouseButton button, ButtonAction action, Supplier<KeyMode[]> mods);

}
