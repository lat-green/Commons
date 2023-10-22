package com.greentree.common.graphics.sgl;


import com.greentree.common.graphics.sgl.SGLFW.GLFWKey;
import com.greentree.common.graphics.sgl.SGLFW.GLFWMouseButton;
import com.greentree.common.graphics.sgl.callback.SGLFWCharCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWCursorPosCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWKeyCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWMouseButtonCallbackI;
import com.greentree.common.graphics.sgl.callback.SGLFWScrollCallbackI;
import com.greentree.common.graphics.sgl.input.IntPairAction;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.action.observer.object.EventAction;


/** @author Arseny Latyshev */
public class Window extends GLFWContext {
	
	private final EventAction<GLFWKey> keyPress, keyRelease, keyRepeat, keyPressOrRepeat;
	private final EventAction<GLFWMouseButton> mouseButtonPress, mouseButtonRelease,
			mouseButtonRepeat;
	
	private final EventAction<Float> mouseScroll;
	private final EventAction<String> charEnter;
	
	private final IntPairAction mousePosition;
	private ListenerCloser keyLC, key1LC, key2LC, charLC, scrollLC, mouseButtonLC, cursorPosLC;
	
	{
		keyPress = new EventAction<>();
		keyRelease = new EventAction<>();
		keyRepeat = new EventAction<>();
		charEnter = new EventAction<>();
		mouseScroll = new EventAction<>();
		keyPressOrRepeat = new EventAction<>();
		
		key1LC = keyPress.addListener(key->keyPressOrRepeat.event(key));
		key2LC = keyRepeat.addListener(key->keyPressOrRepeat.event(key));
		
		keyLC = addKeyCallback((SGLFWKeyCallbackI) (key, scancode, action, mods)-> {
			switch(action) {
				case PRESS -> keyPress.event(key);
				case RELEASE -> keyRelease.event(key);
				case REPEAT -> keyRepeat.event(key);
			}
		});
		charLC = addCharCallback((SGLFWCharCallbackI) codepoint-> {
			charEnter.event(Character.toString(codepoint));
		});
		scrollLC = addScrollCallback((SGLFWScrollCallbackI) (x, y)-> {
			mouseScroll.event((float) y);
		});
		mouseButtonPress = new EventAction<>();
		mouseButtonRelease = new EventAction<>();
		mouseButtonRepeat = new EventAction<>();
		mouseButtonLC = addMouseButtonCallback(
				(SGLFWMouseButtonCallbackI) (button, action, mods)->
				{
					switch(action) {
						case PRESS -> mouseButtonPress.event(button);
						case RELEASE -> mouseButtonRelease.event(button);
						case REPEAT -> mouseButtonRepeat.event(button);
					}
				});
		mousePosition = new IntPairAction();
		cursorPosLC = addCursorPosCallback((SGLFWCursorPosCallbackI) (xpos, ypos)-> {
			mousePosition.action((int) (xpos - getWidth() / 2f), (int) (getHeight() / 2f - ypos));
		});
	}
	
	public Window(final String title, final int width, final int height) {
		this(title, width, height, null);
	}
	
	public Window(final String title, final int width, final int height, final boolean resizable,
			boolean vsync, final boolean fullscreen) {
		super(title, width, height, resizable, fullscreen, true, vsync, null);
	}
	
	public Window(final String title, final int width, final int height, GLFWContext share) {
		super(title, width, height, true, false, true, true, share);
	}
	
	@Override
	public void close() {
		keyLC.close();
		key1LC.close();
		key2LC.close();
		charLC.close();
		scrollLC.close();
		mouseButtonLC.close();
		cursorPosLC.close();
		super.close();
	}
	
	public ObjectObservable<String> getCharEnter() {
		return charEnter;
	}
	
	public ObjectObservable<GLFWKey> getKeyPress() {
		return keyPress;
	}
	
	public ObjectObservable<GLFWKey> getKeyPressOrRepeat() {
		return keyPressOrRepeat;
	}
	
	public ObjectObservable<GLFWKey> getKeyRelease() {
		return keyRelease;
	}
	
	public ObjectObservable<GLFWKey> getKeyRepeat() {
		return keyRepeat;
	}
	
	public ObjectObservable<GLFWMouseButton> getMouseButtonPress() {
		return mouseButtonPress;
	}
	
	public ObjectObservable<GLFWMouseButton> getMouseButtonRelease() {
		return mouseButtonRelease;
	}
	
	public ObjectObservable<GLFWMouseButton> getMouseButtonRepeat() {
		return mouseButtonRepeat;
	}
	
	public IntPairAction getMousePosition() {
		return mousePosition;
	}
	
	public EventAction<Float> getMouseScroll() {
		return mouseScroll;
	}
	
}
