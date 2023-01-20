package com.greentree.commons.util.reference;

import java.io.Serializable;
import java.util.concurrent.locks.StampedLock;

public final class StampedLockVarReference<T, V extends VarReference<T>>
		implements VarReference<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final int PreOptimisticReadCount = 10;
	private final V base;
	
	private final StampedLock lock = new StampedLock();
	
	public StampedLockVarReference(V base) {
		this.base = base;
	}
	
	@Override
	public T get() {
		{
			int t = PreOptimisticReadCount;
			while(t-- > 0) {
				final var stamp = lock.tryOptimisticRead();
				final var value = base.get();
				if(lock.validate(stamp))
					return value;
			}
		}
		final var stamp = lock.readLock();
		try {
			return base.get();
		}finally {
			lock.unlockRead(stamp);
		}
	}
	
	@Override
	public void set(T t) {
		final var stamp = lock.writeLock();
		try {
			base.set(t);
		}finally {
			lock.unlockWrite(stamp);
		}
	}
	
	
	
}
