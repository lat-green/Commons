package com.greentree.common.util.function;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

public abstract class AbstractSaveFunction<T, R> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<T, R> cache = new ConcurrentHashMap<>();
	
	private final GlobalLockSaveFunction<T> locks = new GlobalLockSaveFunction<>();
	
	private final StampedLock lock = new StampedLock();
	
	public final R applyRaw(T t) {
		long stamp, gstamp;
		R result;
		gstamp = lock.readLock();
		try {
			final var lock = locks.applyRaw(t);
			
			stamp = lock.readLock();
			try {
				result = cache.get(t);
				if(validRaw(result))
					return result;
				
				stamp = lock.tryConvertToWriteLock(stamp);
				
				if(stamp == 0)
					throw new IllegalArgumentException("");
				
				result = cache.get(t);
				if(validRaw(result))
					return result;
				result = create(t);
				if(!valid(Objects.requireNonNull(result)))
					throw new UnsupportedOperationException("not valid " + result);
				cache.put(t, result);
				init(result);
				return result;
			}finally {
				lock.unlock(stamp);
			}
		}finally {
			lock.unlockRead(gstamp);
		}
	}
	
	public final void clear() {
		long gstamp;
		gstamp = lock.writeLock();
		try {
			locks.clear();
			cache.clear();
		}finally {
			lock.unlockWrite(gstamp);
		}
	}
	
	@Override
	public String toString() {
		return "SaveFunction [cache=" + cache + "]";
	}
	
	private boolean validRaw(R result) {
		return result != null && valid(result);
	}
	
	protected abstract R create(T t);
	
	protected boolean valid(R result) {
		return true;
	}
	
	protected void init(R result) {
	}
	
	private static final class GlobalLockSaveFunction<T> implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private final Map<T, StampedLock> cache = new ConcurrentHashMap<>();
		
		private final StampedLock lock = new StampedLock();
		
		public StampedLock applyRaw(T t) {
			long stamp;
			StampedLock result;
			
			stamp = lock.readLock();
			try {
				result = cache.get(t);
				if(validRaw(result))
					return result;
				
				stamp = lock.tryConvertToWriteLock(stamp);
				
				if(stamp == 0)
					throw new IllegalArgumentException("");
				
				result = cache.get(t);
				if(validRaw(result))
					return result;
				result = create();
				cache.put(t, result);
				return result;
			}finally {
				lock.unlock(stamp);
			}
		}
		
		public void clear() {
			long stamp;
			stamp = lock.writeLock();
			try {
				cache.clear();
			}finally {
				lock.unlockWrite(stamp);
			}
		}
		
		@Override
		public String toString() {
			return "SaveFunction [cache=" + cache + "]";
		}
		
		private boolean validRaw(StampedLock result) {
			return result != null;
		}
		
		protected StampedLock create() {
			return new StampedLock();
		}
		
	}
	
}
