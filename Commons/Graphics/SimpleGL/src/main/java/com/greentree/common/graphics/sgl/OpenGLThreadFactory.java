package com.greentree.common.graphics.sgl;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public final class OpenGLThreadFactory implements ThreadFactory {
	
	private final ThreadFactory factory;
	private final GLFWContext context;
	
	public OpenGLThreadFactory() {
		this(GLFWContext.currentContext(), Executors.defaultThreadFactory());
	}
	
	public OpenGLThreadFactory(GLFWContext context) {
		this(context, Executors.defaultThreadFactory());
	}
	
	public OpenGLThreadFactory(GLFWContext context, ThreadFactory factory) {
		this.context = context;
		this.factory = factory;
	}
	
	public OpenGLThreadFactory(ThreadFactory factory) {
		this(GLFWContext.currentContext(), factory);
	}
	
	private static Runnable modifyRunnable(GLFWContext context, Runnable r) {
		return new Runnable() {
			
			final GLFWContext c = GLFWContext.createShared(context);
			
			@Override
			public void run() {
				c.makeCurrent();
				r.run();
				GLFWContext.unmakeCurrent();
				c.close();
			}
		};
	}
	
	@Override
	public Thread newThread(Runnable r) {
		r = modifyRunnable(r);
		return factory.newThread(r);
	}
	
	private Runnable modifyRunnable(Runnable r) {
		return modifyRunnable(context, r);
	}
	
}
