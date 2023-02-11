package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

public final class ExecutionSequence implements IntConsumer {
	
	private final AtomicInteger value = new AtomicInteger();
	
	@Override
	public void accept(int value) {
		assertEquals(this.value.getAndIncrement(), value);
	}
	
	
	
}
