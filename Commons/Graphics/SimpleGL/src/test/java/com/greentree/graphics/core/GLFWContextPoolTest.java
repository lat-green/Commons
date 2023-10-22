package com.greentree.graphics.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

import com.greentree.common.graphics.sgl.GLFWContext;
import com.greentree.common.graphics.sgl.GLFWContextExecutor;
import com.greentree.common.graphics.sgl.OpenGLThreadFactory;
import com.greentree.common.graphics.sgl.SGLFW;
import com.greentree.commons.tests.ExecuteCounter;

public class GLFWContextPoolTest {
	
	@Test
	void test1() {
		try(final var count = new ExecuteCounter(10)) {
			SGLFW.init();
			try(final var w = new GLFWContext("Hello", 1, 1, false, false, false, false, null)) {
				final var pool = new GLFWContextExecutor(w, 3);
				var t = 10;
				while(t-- > 0)
					pool.execute(()-> {
						assertTrue(w.isShared(GLFWContext.currentContext()));
						count.run();
					});
			}
			SGLFW.terminate();
		}
	}
	
	@Test
	void test2() {
		try(final var count = new ExecuteCounter(10)) {
			SGLFW.init();
			try(final var w = new GLFWContext("Hello", 1, 1, false, false, false, false, null)) {
				w.makeCurrent();
				final var pool = new GLFWContextExecutor(w, 3);
				var t = 10;
				while(t-- > 0)
					pool.execute(()-> {
						assertTrue(w.isShared(GLFWContext.currentContext()));
						count.run();
					});
				GLFWContext.unmakeCurrent();
			}
			SGLFW.terminate();
		}
	}
	
	@Test
	void test3() throws InterruptedException, ExecutionException {
		try(final var count = new ExecuteCounter(100)) {
			SGLFW.init();
			try(final var w = new GLFWContext("Hello", 1, 1, false, false, false, false, null)) {
				final var pool = new OpenGLThreadFactory(w);
				final var executor = Executors.newFixedThreadPool(5, pool);
				var t = 100;
				final var tasks = new ArrayList<Future<?>>();
				while(t-- > 0)
					tasks.add(executor.submit(()-> {
						assertTrue(w.isShared(GLFWContext.currentContext()), w + " != " + GLFWContext.currentContext());
						count.run();
					}));
				for(var task : tasks)
					task.get();
			}
			SGLFW.terminate();
		}
	}
	
}
