package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowMaximizeCallbackI {

	static SGLFWWindowMaximizeCallbackI create(Iterable<? extends SGLFWWindowMaximizeCallbackI> callbacks) {
		return (maximized)-> {
			for(var c : callbacks)
				c.invoke(maximized);
		};
	}
	
	void invoke(boolean maximized);

}
