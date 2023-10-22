package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWScrollCallbackI {

	static SGLFWScrollCallbackI create(Iterable<? extends SGLFWScrollCallbackI> callbacks) {
		return (xoffset, yoffset)-> {
			for(var c : callbacks)
				c.invoke(xoffset, yoffset);
		};
	}
	
	void invoke(double xoffset, double yoffset);

}
