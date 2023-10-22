package com.greentree.common.graphics.sgl.callback;

@FunctionalInterface
public interface SGLFWDropCallbackI {
	
	static SGLFWDropCallbackI create(Iterable<? extends SGLFWDropCallbackI> callbacks) {
		return (names)-> {
			for(var c : callbacks)
				c.invoke(names);
		};
	}
	
	void invoke(String[] names);
	
}
