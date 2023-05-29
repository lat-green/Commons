package com.greentree.commons.coroutine;

import java.util.concurrent.Future;

public interface CoroutineEnvironment {
	
	Future<?> run(Coroutine coroutine, int maxState);
	
}
