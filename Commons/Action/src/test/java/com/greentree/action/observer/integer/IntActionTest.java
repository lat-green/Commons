package com.greentree.action.observer.integer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.IntConsumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.greentree.commons.action.container.ListenerNotCloseException;
import com.greentree.commons.action.observer.integer.IntAction;

public class IntActionTest {
	
	static class OneRun {
		
		private boolean run_one;
		
		private void assertOneRun() {
			assertFalse(run_one);
			run_one = true;
		}
		
		@BeforeEach
		void setup() {
			run_one = false;
		}
		
		@AfterEach
		void check() {
			assertTrue(run_one);
		}
		
		@Test
		void test1() {
			final var action = new IntAction();
			final var e = 15;
			action.addListener(i-> {
				assertEquals(i, e);
				assertOneRun();
			});
			action.event(e);
		}
		
		@Test
		void test2() {
			final var action = new IntAction();
			final var e = 15;
			final var lc = action.addListener(i-> {
				assertEquals(i, e);
				assertOneRun();
			});
			action.event(e);
			lc.close();
			action.event(e);
		}
		
		@Test
		void test3() {
			final var action = new IntAction();
			final var aEvent = 15;
			final var alc = action.addListener(i-> {
				assertEquals(i, aEvent);
				assertOneRun();
			});
			final var blc = action.addListener(i-> {
				assertEquals(i, aEvent);
			});
			action.event(aEvent);
			alc.close();
			blc.close();
			action.event(aEvent);
		}
		
	}
	
	@Test
	void deleteRef() throws InterruptedException {
		final var action = new IntAction();
		boolean[] finalized = new boolean[1];
		int[] count = new int[1];
		addListener(action, finalized, count);
		Assertions.assertThrows(ListenerNotCloseException.class, ()-> {
			while(!finalized[0]) {
				System.gc();
				action.event(5);
			}
		});
		assertTrue(count[0] < 5);
	}
	
	private void addListener(IntAction action, boolean[] finalized, int[] count) {
		action.addListener(new IntSend(finalized, count));
	}
	
	public record IntSend(boolean[] finalized, int[] count) implements IntConsumer {
		
		@Override
		public void accept(int value) {
			count[0]++;
		}
		
		@Override
		protected void finalize() throws Throwable {
			finalized[0] = true;
		}
		
	}
}
