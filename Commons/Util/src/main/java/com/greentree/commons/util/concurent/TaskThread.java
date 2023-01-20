package com.greentree.commons.util.concurent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

/** @deprecated use {@link Executors#newSingleThreadScheduledExecutor()} */

@Deprecated
public abstract class TaskThread<T> implements AutoCloseable {
	
	private final Queue<T> tasks = new ConcurrentLinkedQueue<>();
	private boolean runninig = true;
	
	
	public TaskThread(String name) {
		Thread thread;
		thread = new Thread(()-> {
			while(runninig || !isEmptyPool())
				pollTasks();
		}, name);
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.setDaemon(true);
		thread.start();
	}
	
	@Override
	public void close() throws Exception {
		runninig = false;
	}
	
	public void add(T operation) {
		tasks.add(operation);
	}
	
	public boolean isEmptyPool() {
		return tasks.isEmpty();
	}
	
	private void pollTasks() {
		final var running_task = tasks.poll();
		run(running_task);
	}
	
	protected abstract void run(T running_task);
	
}
