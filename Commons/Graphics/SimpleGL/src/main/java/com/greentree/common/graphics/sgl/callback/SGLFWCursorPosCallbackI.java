package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWCursorPosCallbackI {

	static SGLFWCursorPosCallbackI create(Iterable<? extends SGLFWCursorPosCallbackI> callbacks) {
		return (x, y)-> {
			for(var c : callbacks)
				c.invoke(x, y);
		};
	}
	
	void invoke(double x, double y);

}
