package com.greentree.commons.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.greentree.commons.util.function.CheckedRunnable;
import com.greentree.commons.util.iterator.IteratorUtil;

public class TestUtil {
	
	public static TaskBuilder builder() {
		return new TaskBuilder();
	}
	
	public static <T> Stream<T> repeat(Collection<? extends T> collection, int count) {
		final var result = new ArrayList<T>();
		while(count-- > 0)
			result.addAll(collection);
		return result.stream();
	}
	
	public static void run(CheckedRunnable... tasks) throws Throwable {
		final var iter = IteratorUtil.iterable(tasks);
		run(IteratorUtil.map(iter, CheckedRunnable::toString), iter);
	}
	
	public static void run(Iterable<String> names, Iterable<CheckedRunnable> tasks) throws Throwable {
		Collection<CheckedRunnable> ts = new ArrayList<>();
		final var names_iter = names.iterator();
		final var threadThrows = new CopyOnWriteArrayList<Throwable>();
		for(CheckedRunnable task : tasks)
			ts.add(run(names_iter.next(), task, threadThrows));
		for(var t : ts)
			t.run();
		tryThrow(threadThrows);
	}
	
	public static void run(String n1, CheckedRunnable t1) throws Throwable {
		run(IteratorUtil.iterable(n1), IteratorUtil.iterable(t1));
	}
	
	public static void run(String n1, CheckedRunnable t1, String n2, CheckedRunnable t2) throws Throwable {
		run(IteratorUtil.iterable(n1, n2), IteratorUtil.iterable(t1, t2));
	}
	
	public static void run(String n1, CheckedRunnable t1, String n2, CheckedRunnable t2, String n3, CheckedRunnable t3)
			throws Throwable {
		run(IteratorUtil.iterable(n1, n2, n3), IteratorUtil.iterable(t1, t2, t3));
	}
	
	public static void testTimeout(long timeout, TimeUnit unit, Runnable task) throws Throwable {
		testTimeout("Unnamed", timeout, unit, task);
	}
	
	public static void testTimeout(String name, long timeout, TimeUnit unit, Runnable task) throws Throwable {
		final var callback = new MultyThrowCallBack();
		final var t = testTimeout0(name, timeout, unit, task, callback);
		t.run();
		callback.print();
	}
	
	public static CheckedRunnable testTimeout0(String name, long timeout, TimeUnit unit, Runnable task,
			ThrowCallBack callback) {
		final var duration = unit.toMillis(timeout);
		if(duration <= 0)
			throw new IllegalArgumentException();
		final var end_timeout = System.currentTimeMillis() + duration;
		final var runned = new boolean[]{true};
		final var taskThrow = new Throwable[]{null};
		final var thread = new Thread(()-> {
			try {
				task.run();
			}catch(Throwable e) {
				taskThrow[0] = e;
			}finally {
				runned[0] = false;
			}
		}, "Task " + name);
		thread.start();
		return ()-> {
			while(runned[0]) {
				if(end_timeout < System.currentTimeMillis()) {
					callback.callbackShy(
							new RuntimeException("timeout " + (System.currentTimeMillis() - end_timeout) + " millis"));
					return;
				}
				Thread.yield();
			}
			thread.join();
			if(taskThrow[0] != null)
				callback.callback(taskThrow[0]);
		};
	}
	
	private static CheckedRunnable run(String name, CheckedRunnable task, Collection<Throwable> threadThrows) {
		final var thread = new Thread(()-> {
			try {
				task.run();
			}catch(Throwable e) {
				threadThrows.add(e);
			}
		}, "Task " + name);
		thread.start();
		return ()-> {
			tryThrow(threadThrows);
			thread.join();
			tryThrow(threadThrows);
		};
	}
	
	private static void tryThrow(Iterable<Throwable> threadThrows) throws Throwable {
		if(!IteratorUtil.isEmpty(threadThrows)) {
			final var e = new RuntimeException("multy exception");
			for(var t : threadThrows)
				e.addSuppressed(t);
			throw e;
		}
	}
	
	public static final class TaskBuilder {
		
		private final Collection<CheckedRunnable> forks = new ArrayList<>();
		
		private final ThrowCallBack callback = new MultyThrowCallBack();
		
		public TaskBuilder fork(long timeout, TimeUnit unit, Runnable task) {
			return fork0("Unnamed", timeout, unit, task);
		}
		
		public TaskBuilder fork(Runnable task) {
			return fork0("Unnamed", Integer.MAX_VALUE, TimeUnit.SECONDS, task);
		}
		
		public TaskBuilder fork(String name, long timeout, TimeUnit unit, Runnable task) {
			return fork0(name, timeout, unit, task);
		}
		
		public TaskBuilder fork(String name, Runnable task) {
			return fork0(name, Integer.MAX_VALUE, TimeUnit.SECONDS, task);
		}
		
		public TaskBuilder fork0(String name, long timeout, TimeUnit unit, Runnable task) {
			final var t = testTimeout0(name, timeout, unit, task, callback);
			forks.add(t);
			return this;
		}
		
		public void join() throws Throwable {
			for(var t : forks)
				t.run();
			forks.clear();
			callback.print();
		}
		
	}
	
	private static final class MultyThrowCallBack implements ThrowCallBack {
		
		private final Collection<Throwable> throwsList = new ArrayList<>();
		private final Collection<Throwable> shyThrowsList = new ArrayList<>();
		
		
		@Override
		public void callback(Throwable e) {
			throwsList.add(e);
		}
		
		@Override
		public void callbackShy(Throwable e) {
			shyThrowsList.add(e);
		}
		
		@Override
		public void print() throws Throwable {
			if(throwsList.isEmpty()) {
				if(shyThrowsList.isEmpty())
					return;
				if(shyThrowsList.size() == 1)
					throw shyThrowsList.iterator().next();
				final var e = new RuntimeException("multy exception: olny shy");
				for(var t : shyThrowsList)
					e.addSuppressed(t);
				throw e;
			}
			if(throwsList.size() == 1) {
				final var e = throwsList.iterator().next();
				for(var t : shyThrowsList)
					e.addSuppressed(t);
				throw e;
			}
			final var e = new RuntimeException("multy exception");
			for(var t : throwsList)
				e.addSuppressed(t);
			throw e;
		}
		
	}
	
	private interface ThrowCallBack {
		
		void callback(Throwable e);
		void callbackShy(Throwable e);
		
		void print() throws Throwable;
		
	}
}
