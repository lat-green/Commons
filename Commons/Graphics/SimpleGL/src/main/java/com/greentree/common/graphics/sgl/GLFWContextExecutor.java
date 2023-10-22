package com.greentree.common.graphics.sgl;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public final class GLFWContextExecutor implements Executor {
	
	private final BlockingQueue<GLFWContext> contexts;
	
	public GLFWContextExecutor(GLFWContext context, int capasity) {
		Objects.requireNonNull(context);
		contexts = new ArrayBlockingQueue<>(capasity);
		while(capasity-- > 0)
			contexts.add(GLFWContext.createShared(context, context.getTitle() + " pool-" + capasity));
	}
	
	public GLFWContextExecutor(int capasity) {
		this(GLFWContext.currentContext(), capasity);
	}
	
	@Override
	public void execute(Runnable r) {
		final var c = GLFWContext.currentContext();
		if(c == null) {
			GLFWContext temp_c;
			try {
				temp_c = contexts.take();
			}catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
			temp_c.makeCurrent();
			r.run();
			GLFWContext.unmakeCurrent();
			contexts.add(temp_c);
		}else {
			GLFWContext temp_c;
			try {
				temp_c = contexts.take();
			}catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
			temp_c.makeCurrent();
			r.run();
			c.makeCurrent();
			contexts.add(temp_c);
		}
	}
	
	
	
}
