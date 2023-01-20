package com.greentree.commons.action.observable;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.util.cortege.Pair;

public interface PairObservable<T1, T2> extends RunObservable, ObjectObservable<Pair<T1, T2>> {

	PairObservable<?, ?> NULL = new PairObservable<>() {
		private static final long serialVersionUID = 1L;

		@Override
		public int listenerSize() {
			return 0;
		}

		@Override
		public ListenerCloser addListener(BiConsumer<? super Object, ? super Object> listener) {
			return ListenerCloser.EMPTY;
		}
	};

	@SuppressWarnings("unchecked")
	static <T1, T2> PairObservable<T1, T2> getNull() {
		return (PairObservable<T1, T2>) NULL;
	}
	
	ListenerCloser addListener(BiConsumer<? super T1, ? super T2> listener);
	
	default ListenerCloser addListener(Runnable listener) {
		return addListener((a, b) -> listener.run());
	}
	
	default ListenerCloser addListener(Consumer<? super Pair<T1, T2>> listener) {
		return addListener((a, b) -> {
			listener.accept(new Pair<>(a, b));
		});
	}
	
}
