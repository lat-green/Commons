package com.greentree.common.graphics.sgl;



import static com.greentree.common.graphics.sgl.SGLFW.Boolean.*;
import static com.greentree.common.graphics.sgl.SGLFW.WindowHint.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import com.greentree.common.graphics.sgl.SGLFW.Boolean;
import com.greentree.common.graphics.sgl.SGLFW.CursorState;
import com.greentree.common.graphics.sgl.SGLFW.InputMode;
import com.greentree.common.graphics.sgl.callback.SGLFWCharCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWCursorPosCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWDropCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWFramebufferSizeCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWKeyCallbackI;
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
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.container.MultiContainer;

public class GLFWContext implements AutoCloseable {
	
	private static final Map<Long, GLFWContext> contexts = new HashMap<>();
	private Thread bindThread;
	
	private final MultiContainer<SGLFWCursorPosCallbackI> cursorPos_callbacks = new MultiContainer<>();
	
	private final MultiContainer<SGLFWDropCallbackI> drop_callbacks = new MultiContainer<>();
	
	private final MultiContainer<SGLFWFramebufferSizeCallbackI> framebufferSize_callbacks = new MultiContainer<>();
	
	private final MultiContainer<SGLFWKeyCallbackI> key_callbacks = new MultiContainer<>();
	
	private final MultiContainer<SGLFWMouseButtonCallbackI> mouseButton_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWScrollCallbackI> scroll_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowCloseCallbackI> windowClose_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowContentScaleCallbackI> contentScale_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowFocusCallbackI> focus_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowIconifyCallbackI> iconify_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowMaximizeCallbackI> maximize_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowPosCallbackI> pos_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowRefreshCallbackI> refresh_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWWindowSizeCallbackI> size_callbacks = new MultiContainer<>();
	private final MultiContainer<SGLFWCharCallbackI> char_callbacks = new MultiContainer<>();
	public final long glID;
	private String title;
	private final DeletePool pool;
	
		public GLFWContext(final String title, final int width, final int height, final boolean resizable,
			final boolean fullscreen, final boolean visible, final boolean vsync, final GLFWContext share) {
		this.title = title;
		
		glfwDefaultWindowHints();
		
		//		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		//		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);
		
		glfwWindowHint(DOUBLEBUFFER.glEnum, TRUE.glEnum);
		glfwWindowHint(VISIBLE.glEnum, FALSE.glEnum);
		glfwWindowHint(SAMPLES.glEnum, 4);
		
		glfwWindowHint(RESIZABLE.glEnum, Boolean.get(resizable).glEnum);
		
		{
			var shareID = share == null ? 0 : share.glID;
			if(share == null)
				pool = new DeletePool();
			else
				pool = share.pool;
			if(fullscreen)
				glID = createWindow(width, height, title, glfwGetPrimaryMonitor(), shareID);
			else {
				glID = createWindow(width, height, title, MemoryUtil.NULL, shareID);
				final var mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
				glfwSetWindowPos(glID, (mode.width() - width) / 2, (mode.height() - height) / 2);
			}
		}
		contexts.put(glID, this);
		final var currentID = glfwGetCurrentContext();
		glfwMakeContextCurrent(glID);
		
		GL.createCapabilities(false);
		
		glfwSwapInterval(vsync ? 1 : 0);
		if(visible)
			glfwShowWindow(glID);
		
		glfwMakeContextCurrent(currentID);
		
		initCallBacks();
	}
	
	public static GLFWContext createShared(GLFWContext context) {
		return createShared(context, context.getTitle() + " Shared");
	}
	
	public static GLFWContext createShared(GLFWContext context, String title) {
		return new GLFWContext(title, 1, 1, false, false, false, false, context);
	}
	
	public static GLFWContext currentContext() {
		return contexts.get(getCurrentContext());
	}
	
	public static long getCurrentContext() {
		return glfwGetCurrentContext();
	}
	
	public static void unmakeCurrent() {
		makeCurrent(0);
	}
	
	public static void updateEvents() {
		glfwPollEvents();
	}
	
	private static long createWindow(int width, int height, String title, long monitor, long share) {
		var id = glfwCreateWindow(width, height, title, monitor, share);
		if(id == MemoryUtil.NULL)
			throw new IllegalStateException("Failed to create the GLFW window " + title + " " + width + "x" + height);
		return id;
	}
	
	public ListenerCloser addCharCallback(SGLFWCharCallbackI callback) {
		return char_callbacks.add(callback);
	}
	
	public ListenerCloser addCloseCallback(SGLFWWindowCloseCallbackI callback) {
		return windowClose_callbacks.add(callback);
	}
	
	public ListenerCloser addContentScaleCallback(SGLFWWindowContentScaleCallbackI callback) {
		return contentScale_callbacks.add(callback);
	}
	
	public ListenerCloser addCursorPosCallback(SGLFWCursorPosCallbackI callback) {
		return cursorPos_callbacks.add(callback);
	}
	
	public ListenerCloser addDropCallback(SGLFWDropCallbackI callback) {
		return drop_callbacks.add(callback);
	}
	
	public ListenerCloser addFocusCallback(SGLFWWindowFocusCallbackI callback) {
		return focus_callbacks.add(callback);
	}
	
	public ListenerCloser addFramebufferSizeCallback(SGLFWFramebufferSizeCallbackI callback) {
		return framebufferSize_callbacks.add(callback);
	}
	
	public ListenerCloser addIconifyCallback(SGLFWWindowIconifyCallbackI callback) {
		return iconify_callbacks.add(callback);
	}
	
	public ListenerCloser addKeyCallback(SGLFWKeyCallbackI callback) {
		return key_callbacks.add(callback);
	}
	
	public ListenerCloser addMaximizeCallback(SGLFWWindowMaximizeCallbackI callback) {
		return maximize_callbacks.add(callback);
	}
	
	public ListenerCloser addMouseButtonCallback(SGLFWMouseButtonCallbackI callback) {
		return mouseButton_callbacks.add(callback);
	}
	
	public ListenerCloser addPosCallback(SGLFWWindowPosCallbackI callback) {
		return pos_callbacks.add(callback);
	}
	
	public ListenerCloser addRefreshCallback(SGLFWWindowRefreshCallbackI callback) {
		return refresh_callbacks.add(callback);
	}
	
	public ListenerCloser addScrollCallback(SGLFWScrollCallbackI callback) {
		return scroll_callbacks.add(callback);
	}
	
	public ListenerCloser addSizeCallback(SGLFWWindowSizeCallbackI callback) {
		return size_callbacks.add(callback);
	}
	
	@Override
	public void close() {
		if(isBind())
			throw new UnsupportedOperationException("destroy current bind GLFWContext");
		contexts.remove(glID);
		glfwDestroyWindow(glID);
	}
	
	public void getCursorPosition(final double[] x, final double[] y) {
		glfwGetCursorPos(glID, x, y);
	}
	
	public int getHeight() {
		var res = new int[1];
		glfwGetWindowSize(glID, null, res);
		return res[0];
	}
	
	public long getMonitor() {
		return glfwGetWindowMonitor(glID);
	}
	
	public int getPositionX() {
		var X = new int[1];
		var Y = new int[1];
		glfwGetWindowPos(glID, X, Y);
		return X[0];
	}
	
	public int getPositionY() {
		var X = new int[1];
		var Y = new int[1];
		glfwGetWindowPos(glID, X, Y);
		return Y[0];
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getWidth() {
		var res = new int[1];
		glfwGetWindowSize(glID, res, null);
		return res[0];
	}
	
	public boolean isBind() {
		return bindThread != null;
	}
	
	public boolean isCurrent() {
		return glfwGetCurrentContext() == glID;
	}
	
	public boolean isShared(GLFWContext c) {
		return c != null && pool == c.pool;
	}
	
	public boolean isShouldClose() {
		return glfwWindowShouldClose(glID);
	}
	
	public void makeCurrent() {
		makeCurrent(glID);
	}
	
	private static void makeCurrent(long glID) {
		final var currentID = glfwGetCurrentContext();
		if(currentID == glID)
			return;
		final var c = contexts.get(currentID);
		if(c != null)
			c.bindThread = null;
		final var n = contexts.get(glID);
		if(n != null) {
			final var currentThread = Thread.currentThread();
			n.bindThread = currentThread;
		}
		glfwMakeContextCurrent(glID);
		
	}
	
	public void pollDelete() {
		pool.pollDelete();
	}
	
	public void setCursorMode(CursorState state) {
		glfwSetInputMode(glID, InputMode.CURSOR.glEnum, state.glEnum);
	}
	
	public void setCursorPosition(int x, int y) {
		glfwSetCursorPos(glID, x + getWidth() / 2, y + getHeight() / 2);
	}
	
	public void setPosition(int x, int y) {
		glfwSetWindowPos(glID, x, y);
	}
	
	public void setTitle(String title) {
		this.title = title;
		glfwSetWindowTitle(glID, title);
	}
	
	public void shouldClose() {
		glfwSetWindowShouldClose(glID, true);
	}
	
	public void swapBuffer() {
		glfwSwapBuffers(glID);
	}
	
	@Override
	public String toString() {
		return "GLFWContext [id=" + glID + ", title=" + title + "]";
	}
	
	private void initCallBacks() {
		glfwSetCharCallback(glID, GLFWCallback.gl(SGLFWCharCallbackI.create(char_callbacks)));
		glfwSetDropCallback(glID, GLFWCallback.gl(SGLFWDropCallbackI.create(drop_callbacks)));
		glfwSetFramebufferSizeCallback(glID,
				GLFWCallback.gl(SGLFWFramebufferSizeCallbackI.create(framebufferSize_callbacks)));
		glfwSetScrollCallback(glID, GLFWCallback.gl(SGLFWScrollCallbackI.create(scroll_callbacks)));
		glfwSetKeyCallback(glID, GLFWCallback.gl(SGLFWKeyCallbackI.create(key_callbacks)));
		glfwSetCursorPosCallback(glID, GLFWCallback.gl(SGLFWCursorPosCallbackI.create(cursorPos_callbacks)));
		glfwSetMouseButtonCallback(glID, GLFWCallback.gl(SGLFWMouseButtonCallbackI.create(mouseButton_callbacks)));
		glfwSetWindowCloseCallback(glID, GLFWCallback.gl(SGLFWWindowCloseCallbackI.create(windowClose_callbacks)));
		glfwSetWindowContentScaleCallback(glID,
				GLFWCallback.gl(SGLFWWindowContentScaleCallbackI.create(contentScale_callbacks)));
		glfwSetWindowFocusCallback(glID, GLFWCallback.gl(SGLFWWindowFocusCallbackI.create(focus_callbacks)));
		glfwSetWindowMaximizeCallback(glID, GLFWCallback.gl(SGLFWWindowMaximizeCallbackI.create(maximize_callbacks)));
		glfwSetWindowIconifyCallback(glID, GLFWCallback.gl(SGLFWWindowIconifyCallbackI.create(iconify_callbacks)));
		glfwSetWindowRefreshCallback(glID, GLFWCallback.gl(SGLFWWindowRefreshCallbackI.create(refresh_callbacks)));
		glfwSetWindowSizeCallback(glID, GLFWCallback.gl(SGLFWWindowSizeCallbackI.create(size_callbacks)));
		glfwSetWindowPosCallback(glID, GLFWCallback.gl(SGLFWWindowPosCallbackI.create(pos_callbacks)));
	}
	
	void putOnDelete(GLObject object) {
		pool.putOnDelete(object);
	}
	
	private static final class DeletePool {
		
		private final Queue<GLObject> queue = new ConcurrentLinkedQueue<>();
		
		public void pollDelete() {
			while(true) {
				final var obj = queue.poll();
				if(obj == null)
					return;
				obj.delete();
			}
		}
		
		public void putOnDelete(GLObject object) {
			queue.add(object);
		}
		
	}
	
}
