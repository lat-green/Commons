package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWFramebufferSizeCallbackI {

	static SGLFWFramebufferSizeCallbackI create(Iterable<? extends SGLFWFramebufferSizeCallbackI> callbacks) {
		return (width, height)-> {
			for(var c : callbacks)
				c.invoke(width, height);
		};
	}
	
	void invoke(int width, int height);

}
