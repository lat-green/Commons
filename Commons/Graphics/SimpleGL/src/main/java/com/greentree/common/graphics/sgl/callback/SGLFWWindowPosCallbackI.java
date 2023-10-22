package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowPosCallbackI {
	
	static SGLFWWindowPosCallbackI create(Iterable<? extends SGLFWWindowPosCallbackI> callbacks) {
		return (xpos, ypos)-> {
			for(var c : callbacks)
				c.invoke(xpos, ypos);
		};
	}
	
	void invoke(int xpos, int ypos);
	
}
