package com.greentree.action.observable;

import java.io.Serializable;

import com.greentree.action.ListenerCloser;

public interface RunObservable extends Serializable {

	RunObservable NULL = new RunObservable() {
		private static final long serialVersionUID = 1L;

		@Override
		public int listenerSize() {
			return 0;
		}
		
		@Override
		public ListenerCloser addListener(Runnable listener) {
			return ListenerCloser.EMPTY;
		}
	};
	
	ListenerCloser addListener(Runnable listener);
	int listenerSize();
	
	default boolean isEmpty() {
		return listenerSize() == 0;
	}
	
}
