package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowCloseCallbackI {

	static SGLFWWindowCloseCallbackI create(Iterable<? extends SGLFWWindowCloseCallbackI> callbacks) {
		return ()-> {
			for(var c : callbacks)
				c.invoke();
		};
	}
	
	void invoke();

}
