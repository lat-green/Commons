package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowRefreshCallbackI {

	static SGLFWWindowRefreshCallbackI create(Iterable<? extends SGLFWWindowRefreshCallbackI> callbacks) {
		return ()-> {
			for(var c : callbacks)
				c.invoke();
		};
	}
	
	void invoke();

}
