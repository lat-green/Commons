package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowSizeCallbackI {

	static SGLFWWindowSizeCallbackI create(Iterable<? extends SGLFWWindowSizeCallbackI> callbacks) {
		return (width, height)-> {
			for(var c : callbacks)
				c.invoke(width, height);
		};
	}
	
	void invoke(int width, int height);

}
