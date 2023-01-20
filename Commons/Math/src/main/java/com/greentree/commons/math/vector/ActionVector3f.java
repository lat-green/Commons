package com.greentree.commons.math.vector;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.action.observer.pair.PairAction;

public class ActionVector3f extends AbstractVector3f implements VectorAction {
	
	private static final long serialVersionUID = 1L;
	
	private transient boolean actionX, actionY, actionZ;
	
	private transient final EventAction<ActionVector3f> action = new EventAction<>();
	private transient final PairAction<AbstractVector3f, AbstractVector3f> changeAction = new PairAction<>();
	private final ActionFloat x, y, z;
	
	private transient AbstractVector3f old;
	
	public ActionVector3f() {
		this(0);
	}
	
	public ActionVector3f(AbstractVector3f f) {
		this(f.x(), f.y(), f.z());
	}
	
	public ActionVector3f(float f) {
		this(f, f, f);
	}
	
	public ActionVector3f(float x, float y, float z) {
		this.x = new ActionFloat();
		this.y = new ActionFloat();
		this.z = new ActionFloat();
		this.x.addListener(l->actionX = true);
		this.y.addListener(l->actionY = true);
		this.z.addListener(l->actionZ = true);
		set(x, y, z);
		old = new Vector3f(this);
	}
	
	public ListenerCloser addChangeListener(BiConsumer<AbstractVector3f, AbstractVector3f> l) {
		final var lc = changeAction.addListener(l);
		action();
		return lc;
	}
	
	public ListenerCloser addListener(Consumer<ActionVector3f> l) {
		final var lc = action.addListener(l);
		action();
		return lc;
	}
	
	@Override
	public ActionVector3f set(AbstractVector3f vec) {
		set(vec.x(), vec.y(), vec.z());
		return this;
	}
	
	@Override
	public void x(float x) {
		this.x.set(x);
	}
	
	@Override
	public void y(float y) {
		this.y.set(y);
	}
	
	@Override
	public void z(float z) {
		this.z.set(z);
	}
	
	public final void tryAction() {
		if(actionX || actionY || actionZ)
			action();
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
	
	@Override
	public float z() {
		tryAction();
		return z.get();
	}
	
	private final void action() {
		actionX = false;
		actionY = false;
		actionZ = false;
		action.event(this);
		changeAction.event(old, this);
		old = new Vector3f(this);
	}
}
