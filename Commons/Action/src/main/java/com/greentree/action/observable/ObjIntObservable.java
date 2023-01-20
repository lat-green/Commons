package com.greentree.action.observable;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

import com.greentree.action.ListenerCloser;

public interface ObjIntObservable<T> extends RunObservable, IntObservable, ObjectObservable<T> {
	
	ObjIntObservable<?> NULL = new ObjIntObservable<>() {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public ListenerCloser addListener(ObjIntConsumer<? super Object> listener) {
			return ListenerCloser.EMPTY;
		}
		
		@Override
		public int listenerSize() {
			return 0;
		}
		
	};
	
	@SuppressWarnings("unchecked")
	static <T> ObjIntObservable<T> getNull() {
		return (ObjIntObservable<T>) NULL;
	}
	
	@Override
	default ListenerCloser addListener(Runnable listener) {
		return addListener((e, i)->listener.run());
	}
	
	@Override
	default ListenerCloser addListener(Consumer<? super T> listener) {
		return addListener((e, i)->listener.accept(e));
	}
	
	@Override
	default ListenerCloser addListener(IntConsumer listener) {
		return addListener((e, i)->listener.accept(i));
	}
	
	ListenerCloser addListener(ObjIntConsumer<? super T> listener);
	
}
