package com.greentree.commons.coroutine;

import java.util.concurrent.Future;

public class BaseCoroutineEnvironment implements CoroutineEnvironment {
	
	@Override
	public Future<?> run(Coroutine coroutine, int maxState) {
		for(int i = 0; i < maxState; i++) {
			var e = coroutine.run(i);
			while(!e.isDone())
				;
		}
		return null;
	}
	
}
