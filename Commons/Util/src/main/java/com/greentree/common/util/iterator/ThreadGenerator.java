package com.greentree.common.util.iterator;

import static com.greentree.common.util.TernarBoolean.*;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.greentree.common.util.TernarBoolean;
import com.greentree.common.util.exception.WrappedException;

public class ThreadGenerator<T> implements Iterator<T> {
	
	private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool(r-> {
		final var thread = new Thread(r);
		thread.setDaemon(true);
		return thread;
	});
	
	private T next;
	private TernarBoolean hasNext = MAYBE;
	
	private boolean _hasNext;
	
	
	public ThreadGenerator(Consumer<Consumer<T>> iter) {
		EXECUTOR_SERVICE.submit(()-> {
			synchronized(hasNext) {
				hasNext.notify();
			}
			synchronized(hasNext) {
				try {
					hasNext.wait();
				}catch(InterruptedException e) {
					throw new WrappedException(e);
				}
			}
			iter.accept(t-> {
				synchronized(hasNext) {
					synchronized(next) {
						next = t;
					}
					hasNext.notify();
					hasNext = TRUE;
					try {
						hasNext.wait();
					}catch(InterruptedException e) {
						throw new WrappedException(e);
					}
				}
			});
			synchronized(hasNext) {
				hasNext.notify();
				hasNext = FALSE;
			}
		});
		synchronized(hasNext) {
			try {
				hasNext.wait();
			}catch(InterruptedException e) {
				throw new WrappedException(e);
			}
		}
		_hasNext = gethasNext();
	}
	
	@Override
	public boolean hasNext() {
		return _hasNext;
	}
	
	@Override
	public T next() {
		if(hasNext != FALSE) {
			final T res;
			synchronized(next) {
				res = next;
				next = null;
			}
			_hasNext = gethasNext();
			return res;
		}
		return null;
	}
	
	private boolean gethasNext() {
		synchronized(hasNext) {
			hasNext.notify();
			try {
				hasNext.wait();
			}catch(InterruptedException e) {
				throw new WrappedException(e);
			}
			if(hasNext == TRUE) {
				hasNext = MAYBE;
				return true;
			}
			if(hasNext == FALSE)
				return false;
		}
		throw new UnsupportedOperationException();
	}
	
	
}
