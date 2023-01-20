package com.greentree.commons.math.vector;

import java.util.function.Consumer;

import com.greentree.commons.action.observer.object.EventAction;

public class VectorAction1f extends AbstractVector1f implements VectorAction {
	
	private boolean actionX;
	
	private final EventAction<VectorAction1f> action = new EventAction<>();
	private final ActionFloat x;
	
	private static final long serialVersionUID = 1L;
	
	public VectorAction1f(float x) {
		this.x = new ActionFloat();
		this.x.set(x);
		this.x.addListener(l->actionX = true);
	}
	
	public void addListener(Consumer<VectorAction1f> l) {
		action.addListener(l);
	}
	
	@Override
	public void setX(float x) {
		this.x.set(x);
	}
	
	public final void tryAction() {
		if(actionX) {
			actionX = false;
			action.event(this);
		}
	}
	
	@Override
	public float x() {
		tryAction();
		return x.get();
	}
	
}
