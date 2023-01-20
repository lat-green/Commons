package com.greentree.commons.action.observable;

import java.util.function.IntConsumer;

import com.greentree.commons.action.ListenerCloser;

public interface IntObservable extends RunObservable {
	
	IntObservable NULL = new IntObservable() {
		private static final long serialVersionUID = 1L;

		@Override
		public int listenerSize() {
			return 0;
		}
		
		@Override
		public ListenerCloser addListener(IntConsumer listener) {
			return ListenerCloser.EMPTY;
		}
	};
	
	@Override
	default ListenerCloser addListener(Runnable listener) {
		return addListener(i -> listener.run());
	}
	
	ListenerCloser addListener(IntConsumer listener);
	
}
