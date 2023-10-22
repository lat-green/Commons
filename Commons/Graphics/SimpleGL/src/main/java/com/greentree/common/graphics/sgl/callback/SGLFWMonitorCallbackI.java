package com.greentree.common.graphics.sgl.callback;

import com.greentree.common.graphics.sgl.SGLFW.ConnectedEvent;

@FunctionalInterface
public interface SGLFWMonitorCallbackI {

	static SGLFWMonitorCallbackI create(Iterable<? extends SGLFWMonitorCallbackI> callbacks) {
		return (monitor, event)-> {
			for(var c : callbacks)
				c.invoke(monitor, event);
		};
	}
	
	void invoke(long monitor, ConnectedEvent event);

}
