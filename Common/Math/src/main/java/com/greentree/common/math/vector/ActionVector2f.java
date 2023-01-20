package com.greentree.common.math.vector;

import java.util.function.Consumer;

import com.greentree.action.observer.object.EventAction;

public class ActionVector2f extends AbstractVector2f implements VectorAction {

	private boolean actionX, actionY;

	private final EventAction<ActionVector2f> action = new EventAction<>();
	private final ActionFloat x, y;

	private static final long serialVersionUID = 1L;

	public ActionVector2f(float x, float y) {
		this.x = new ActionFloat();
		this.x.set(x);
		this.x.addListener(l -> actionX = true);
		
		this.y = new ActionFloat();
		this.y.set(y);
		this.y.addListener(l -> actionY = true);
	}
	public ActionVector2f(float f) {
		this(f, f);
	}
	public ActionVector2f() {
		this(0);
	}
	
	public void addListener(Consumer<ActionVector2f> l){
		action.addListener(l);
	}

	@Override
	public void x(float x) {
		this.x.set(x);
	}

	@Override
	public void y(float y) {
		this.y.set(y);
	}

	public final void tryAction(){
		if(actionX || actionY) {
			actionX = false;
			actionY = false;
			action.event(this);
		}
	}

	@Override
	public float x() {
		tryAction();
		return x.get();
	}

	@Override
	public float y() {
		tryAction();
		return y.get();
	}

}
