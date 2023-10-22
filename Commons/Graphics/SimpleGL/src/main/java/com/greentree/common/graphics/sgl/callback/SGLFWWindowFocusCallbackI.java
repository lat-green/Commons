package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowFocusCallbackI {

	static SGLFWWindowFocusCallbackI create(Iterable<? extends SGLFWWindowFocusCallbackI> callbacks) {
		return (focused)-> {
			for(var c : callbacks)
				c.invoke(focused);
		};
	}
	
	void invoke(boolean focused);

}
