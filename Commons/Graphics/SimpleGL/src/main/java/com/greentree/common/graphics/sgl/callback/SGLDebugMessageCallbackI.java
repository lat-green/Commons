package com.greentree.common.graphics.sgl.callback;

import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSeverity;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSource;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugType;

@FunctionalInterface
public interface SGLDebugMessageCallbackI {

	static SGLDebugMessageCallbackI create(Iterable<? extends SGLDebugMessageCallbackI> callbacks) {
		return (source, type, id, severity, message, userParam)-> {
			for(var c : callbacks)
				c.invoke(source, type, id, severity, message, userParam);
		};
	}
	
	void invoke(GLDebugSource source, GLDebugType type, int id, GLDebugSeverity severity, String message, long userParam);

}
