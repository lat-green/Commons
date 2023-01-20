package com.greentree.commons.action.observer.object;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/** @author Arseny Latyshev */
public class AsyncEventAction<T> extends EventAction<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final ExecutorService executor;
	
	public AsyncEventAction(ExecutorService executor) {
		this.executor = executor;
	}
	
	@Override
	public void event(T t) {
		final var futures = new ArrayList<Future<?>>(listeners.size());
		for(var l : listeners) {
			final var f = executor.submit(()-> {
				l.accept(t);
			});
			futures.add(f);
		}
		for(var f : futures) {
			try {
				f.get();
			}catch(InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
