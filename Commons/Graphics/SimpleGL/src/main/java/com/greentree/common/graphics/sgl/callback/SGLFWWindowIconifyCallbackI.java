package com.greentree.common.graphics.sgl.callback;


@FunctionalInterface
public interface SGLFWWindowIconifyCallbackI {

	static SGLFWWindowIconifyCallbackI create(Iterable<? extends SGLFWWindowIconifyCallbackI> callbacks) {
		return (iconified)-> {
			for(var c : callbacks)
				c.invoke(iconified);
		};
	}
	
	void invoke(boolean iconified);

}
