package com.greentree.common.util.concurent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import com.greentree.common.util.iterator.IteratorUtil;

@Deprecated
/** @author Arseny Latyshev */
public abstract class MultiTask {
	
	public static final ExecutorService EXECUTOR = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r->
			{
				final var thread = new Thread(r);
				thread.setDaemon(true);
				return thread;
			});
			
	public static <R> Collection<R> get(final Iterable<Future<R>> futures)
			throws InterruptedException, ExecutionException {
		Collection<R> collection = new ArrayList<>();
		for(var f : futures)
			collection.add(f.get());
		return collection;
	}
	
	public static <V> Future<V> task(final Callable<V> run) {
		return EXECUTOR.submit(run);
	}
	
	public static void task(final Iterable<Runnable> run)
			throws InterruptedException, ExecutionException {
		final var futures = task(run, r-> {
			r.run();
		});
		
		wait(futures);
	}
	
	public static <T> Collection<Future<?>> task(final Iterable<T> collection,
			final Consumer<T> run) {
		Collection<Future<?>> futures = new ArrayList<>();
		for(var t : collection)
			futures.add(task(()->run.accept(t)));
		return futures;
	}
	
	public static <T, R> Collection<Future<R>> task(final Iterable<T> collection,
			final Function<T, R> func) {
		Collection<Future<R>> futures = new ArrayList<>();
		for(var t : collection)
			futures.add(task(()->func.apply(t)));
		return futures;
	}
	
	public static void task(final Runnable... run) throws InterruptedException, ExecutionException {
		task(IteratorUtil.iterable(run));
	}
	
	public static Future<?> task(final Runnable run) {
		return EXECUTOR.submit(run);
	}
	
	public static <T1, V> Future<V> task(final T1 t1, final Function<T1, V> function) {
		return EXECUTOR.submit(()->function.apply(t1));
	}
	
	public static <T1, T2, V> Future<V> task(final T1 t1, final T2 t2,
			final BiFunction<T1, T2, V> function) {
		return EXECUTOR.submit(()->function.apply(t1, t2));
	}
	
	public static void terminate() {
		EXECUTOR.shutdown();
	}
	
	public static void wait(final Future<?>... futures)
			throws InterruptedException, ExecutionException {
		for(var f : futures)
			f.get();
	}
	
	public static void wait(final Iterable<Future<?>> futures)
			throws InterruptedException, ExecutionException {
		for(var f : futures)
			f.get();
	}
	
}
