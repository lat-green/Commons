package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWCharCallbackI {

	static SGLFWCharCallbackI create(Iterable<? extends SGLFWCharCallbackI> callbacks) {
		return (chars)-> {
			for(var c : callbacks)
				c.invoke(chars);
		};
	}
	
	void invoke(int chars);

}
