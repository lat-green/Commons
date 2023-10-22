package com.greentree.common.graphics.sgl.callback;

import com.greentree.common.graphics.sgl.SGLFW.Error;

@FunctionalInterface
public interface SGLFWErrorCallbackI {

	static SGLFWErrorCallbackI create(Iterable<? extends SGLFWErrorCallbackI> callbacks) {
		return (glfwError, description)-> {
			for(var c : callbacks)
				c.invoke(glfwError, description);
		};
	}
	
	void invoke(Error glfwError, String description);

}
