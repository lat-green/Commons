package com.greentree.common.graphics.sgl.callback;

import com.greentree.common.graphics.sgl.SGLFW.ConnectedEvent;

@FunctionalInterface
public interface SGLFWJoystickCallbackI {

	static SGLFWJoystickCallbackI create(Iterable<? extends SGLFWJoystickCallbackI> callbacks) {
		return (jid, event)-> {
			for(var c : callbacks)
				c.invoke(jid, event);
		};
	}
	
	void invoke(int jid, ConnectedEvent event);

}
