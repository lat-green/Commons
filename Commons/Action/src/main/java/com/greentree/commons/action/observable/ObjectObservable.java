package com.greentree.commons.action.observable;

import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;

public interface ObjectObservable<T> extends RunObservable {

	ObjectObservable<?> NULL = new ObjectObservable<>() {
		private static final long serialVersionUID = 1L;

		@Override
		public ListenerCloser addListener(Consumer<? super Object> listener) {
			return ListenerCloser.EMPTY;
		}

		@Override
		public int listenerSize() {
			return 0;
		}

	};

	@SuppressWarnings("unchecked")
	static <T> ObjectObservable<T> getNull() {
		return (ObjectObservable<T>) NULL;
	}

	ListenerCloser addListener(Consumer<? super T> listener);

	@Override
	default ListenerCloser addListener(Runnable listener) {
		return addListener(t -> listener.run());
	}

}
