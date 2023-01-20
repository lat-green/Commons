package com.greentree.common.util.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.greentree.common.util.iterator.IteratorUtil;

public final class MultiException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<? extends Throwable> exceptions;
	
	
	public MultiException(Iterable<? extends Throwable> exceptions) {
		super("", null, true, false);
		this.exceptions = exceptions;
	}
	
	public MultiException(Object message, Iterable<? extends Throwable> exceptions) {
		super(message.toString(), null, true, true);
		this.exceptions = exceptions;
	}
	
	public MultiException(Object message, Throwable... exceptions) {
		this(message, IteratorUtil.iterable(exceptions));
	}
	
	@Deprecated
	public <T extends Throwable> MultiException(Throwable exceptions) {
		this(new Throwable[]{exceptions});
	}
	
	public MultiException(Throwable... exceptions) {
		this(IteratorUtil.iterable(exceptions));
	}
	
	/** @deprecated use {@link MultiException#getThrowables()} */
	@Deprecated
	@Override
	public synchronized Throwable getCause() {
		return null;
	}
	
	public synchronized Iterable<? extends Throwable> getThrowables() {
		return exceptions;
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		try(final var out = new PrintWriter(s);) {
			printStackTrace(out);
		}
	}
	
	@Override
	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
		for(var e : exceptions)
			if(e != null)
				e.printStackTrace(s);
	}
	
	@Override
	public String toString() {
		String s = getClass().getName();
		String message = getLocalizedMessage();
		String size = (IteratorUtil.isEmpty(exceptions) ? ""
				: "(" + IteratorUtil.size(exceptions) + ")");
		return (message != null) ? (s + ": " + message) : s + size;
	}
	
}
