package com.greentree.commons.action.observer.run;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AsyncRunAction extends RunAction {
	
	private static final long serialVersionUID = 1L;
	
	private final ExecutorService executor;
	
	public AsyncRunAction(ExecutorService executor) {
		this.executor = executor;
	}
	
	@Override
	public void event() {
		final var futures = new ArrayList<Future<?>>(listeners.size());
		for(var l : listeners) {
			final var f = executor.submit(()-> {
				l.run();
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
