package com.greentree.common.graphics.sgl;

import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWFramebufferSizeCallbackI;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowContentScaleCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowMaximizeCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.GLDebugMessageCallbackI;

import com.greentree.common.graphics.sgl.SGLFW.ButtonAction;
import com.greentree.common.graphics.sgl.SGLFW.ConnectedEvent;
import com.greentree.common.graphics.sgl.SGLFW.Error;
import com.greentree.common.graphics.sgl.SGLFW.GLFWKey;
import com.greentree.common.graphics.sgl.SGLFW.GLFWMouseButton;
import com.greentree.common.graphics.sgl.SGLFW.KeyMode;
import com.greentree.common.graphics.sgl.callback.SGLDebugMessageCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWCharCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWCursorPosCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWDropCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWErrorCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWFramebufferSizeCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWJoystickCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWKeyCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWMonitorCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWMouseButtonCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWScrollCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowCloseCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowContentScaleCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowFocusCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowIconifyCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowMaximizeCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowPosCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowRefreshCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWWindowSizeCallbackI;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSeverity;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugSource;
import com.greentree.common.graphics.sgl.enums.gl.debug.GLDebugType;


public class GLFWCallback {
	
	public static GLDebugMessageCallbackI gl(SGLDebugMessageCallbackI callback) {
		return (source, type, id, severity, length, message, userParam) -> {
			callback.invoke(GLDebugSource.get(source), GLDebugType.get(type), id, GLDebugSeverity.get(severity), GLDebugMessageCallback.getMessage(length, message), userParam);
		};
	}
	public static GLFWCharCallbackI gl(SGLFWCharCallbackI callback) {
		return (window, codepoint)-> {
			callback.invoke(codepoint);
		};
	}
	
	public static GLFWCursorPosCallbackI gl(SGLFWCursorPosCallbackI callback) {
		return (window, x, y) -> {
			callback.invoke(x, y);
		};
	}
	public static GLFWDropCallbackI gl(SGLFWDropCallbackI callback) {
		return new GLFWDropCallback() {
			
			@Override
			public void invoke(long window, int count, long names) {
				final var strNames = new String[count];
				for(var i = 0; i < count; i++)
					strNames[i] = getName(names, i);
				callback.invoke(strNames);
			}
		};
	}
	public static GLFWErrorCallbackI gl(SGLFWErrorCallbackI callback) {
		return (error, description) -> {
			callback.invoke(Error.get(error), GLFWErrorCallback.getDescription(description));
		};
	}
	public static GLFWFramebufferSizeCallbackI gl(SGLFWFramebufferSizeCallbackI callback) {
		return (window, width, height) -> {
			callback.invoke(width, height);
		};
	}
	public static GLFWJoystickCallbackI gl(SGLFWJoystickCallbackI callback) {
		return (jid, event) -> {
			callback.invoke(jid, ConnectedEvent.get(event));
		};
	}
	public static GLFWKeyCallbackI gl(SGLFWKeyCallbackI callback) {
		return (window, key, scancode, action, mods) -> {
			callback.invoke(GLFWKey.get(key), scancode, ButtonAction.get(action), () -> KeyMode.gl(mods));
		};
	}
	public static GLFWMonitorCallbackI gl(SGLFWMonitorCallbackI callback) {
		return (monitor, event) -> {
			callback.invoke(monitor, ConnectedEvent.get(event));
		};
	}
	public static GLFWMouseButtonCallbackI gl(SGLFWMouseButtonCallbackI callback) {
		return (window, button, action, mods) -> {
			callback.invoke(GLFWMouseButton.get(button), ButtonAction.get(action), () -> KeyMode.gl(mods));
		};
	}
	public static GLFWScrollCallbackI gl(SGLFWScrollCallbackI callback) {
		return (window, xoffset, yoffset) -> {
			callback.invoke(xoffset, yoffset);
		};
	}
	public static GLFWWindowCloseCallbackI gl(SGLFWWindowCloseCallbackI callback) {
		return window-> {
			callback.invoke();
		};
	}
	public static GLFWWindowContentScaleCallbackI gl(SGLFWWindowContentScaleCallbackI callback) {
		return (window, xscale, yscale) -> {
			callback.invoke(xscale, yscale);
		};
	}
	public static GLFWWindowFocusCallbackI gl(SGLFWWindowFocusCallbackI callback) {
		return (window, focused) -> {
			callback.invoke(focused);
		};
	}
	public static GLFWWindowIconifyCallbackI gl(SGLFWWindowIconifyCallbackI callback) {
		return (window, iconified) -> {
			callback.invoke(iconified);
		};
	}
	public static GLFWWindowMaximizeCallbackI gl(SGLFWWindowMaximizeCallbackI callback) {
		return (window, maximized) -> {
			callback.invoke(maximized);
		};
	}
	public static GLFWWindowPosCallbackI gl(SGLFWWindowPosCallbackI callback) {
		return (window, xpos, ypos) -> {
			callback.invoke(xpos, ypos);
		};
	}
	public static GLFWWindowRefreshCallbackI gl(SGLFWWindowRefreshCallbackI callback) {
		return window-> {
			callback.invoke();
		};
	}
	public static GLFWWindowSizeCallbackI gl(SGLFWWindowSizeCallbackI callback) {
		return (window, width, height) -> {
			callback.invoke(width, height);
		};
	}
	
}
