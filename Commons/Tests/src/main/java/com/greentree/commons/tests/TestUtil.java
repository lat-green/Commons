package com.greentree.commons.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import com.greentree.commons.util.function.CheckedRunnable;
import com.greentree.commons.util.iterator.IteratorUtil;

public class TestUtil {
	
	public static <T> Stream<T> repeat(Collection<? extends T> collection, int count) {
		final var result = new ArrayList<T>();
		while(count-- > 0) {
			for(var e : collection)
				result.add(e);
		}
		return result.stream();
	}
	
	public static void run(CheckedRunnable... tasks) throws Throwable {
		run(IteratorUtil.iterable(tasks));
	}
	
	public static void run(Iterable<CheckedRunnable> tasks) throws Throwable {
		Collection<CheckedRunnable> ts = new ArrayList<>();
		for(var t : tasks)
			ts.add(t);
		final var threadThrows = new CopyOnWriteArrayList<Throwable>();
		ts = ts.stream().map(x->run(x, threadThrows)).toList();
		ts = ts.stream().map(x->run(x, threadThrows)).toList();
		for(var t : ts)
			t.run();
		tryThrow(threadThrows);
	}
	
	private static void tryThrow(Iterable<Throwable> threadThrows) throws Throwable {
		final var iter = threadThrows.iterator();
		if(iter.hasNext())
			throw iter.next();
	}
	
	private static CheckedRunnable run(CheckedRunnable task, Collection<Throwable> threadThrows) {
		final var thread = new Thread(()-> {
			try {
				task.run();
			}catch(Throwable e) {
				threadThrows.add(e);
			}
		}, "Task");
		thread.start();
		return ()-> {
			tryThrow(threadThrows);
			thread.join();
			tryThrow(threadThrows);
		};
	}
}
