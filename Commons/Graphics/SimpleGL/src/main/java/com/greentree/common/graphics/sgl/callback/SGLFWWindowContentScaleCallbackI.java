package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowContentScaleCallbackI {

	static SGLFWWindowContentScaleCallbackI create(Iterable<? extends SGLFWWindowContentScaleCallbackI> callbacks) {
		return (xscale, yscale)-> {
			for(var c : callbacks)
				c.invoke(xscale, yscale);
		};
	}
	
	void invoke(float xscale, float yscale);

}
