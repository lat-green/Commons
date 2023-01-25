package com.greentree.common.assets.file;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;


public final class NullExecutorService extends AbstractExecutorService {
	
	@Override
	public void shutdown() {
		
	}
	
	@Override
	public List<Runnable> shutdownNow() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean isShutdown() {
		return false;
	}
	
	@Override
	public boolean isTerminated() {
		return false;
	}
	
	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return true;
	}
	
	@Override
	public void execute(Runnable command) {
	}
	
	
	
}
