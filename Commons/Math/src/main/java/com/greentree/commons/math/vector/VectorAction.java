package com.greentree.commons.math.vector;

import java.io.Serializable;

import com.greentree.commons.action.observer.object.EventAction;

public interface VectorAction {
	
	public static class ActionFloat extends EventAction<Float> implements Serializable {
		
		private float f;
		private static final long serialVersionUID = 1L;
		
		public float get() {
			return f;
		}
		
		public void set(float f) {
			if(f == this.f)
				return;
			event(f);
			this.f = f;
		}
	}
	
}
