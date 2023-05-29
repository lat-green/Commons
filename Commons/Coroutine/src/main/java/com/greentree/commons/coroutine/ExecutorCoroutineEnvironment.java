package com.greentree.commons.coroutine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public record ExecutorCoroutineEnvironment(ExecutorService executor) implements CoroutineEnvironment {
	
	@Override
	public Future<?> run(Coroutine coroutine, int maxState) {
		return executor.submit(() -> {
			coroutine.run(expretion -> {
				while(!expretion.isDone())
					;
			});
		});
	}
	
}
