package com.greentree.common.util.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class WrappedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	
	public <T extends Throwable> WrappedException(Throwable cause) {
		super(null, cause, true, false);
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		getCause().printStackTrace(s);
	}
	
	@Override
	public void printStackTrace(PrintWriter s) {
		getCause().printStackTrace(s);
	}
	
}
