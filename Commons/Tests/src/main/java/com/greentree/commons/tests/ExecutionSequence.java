package com.greentree.commons.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.IntConsumer;

public final class ExecutionSequence implements IntConsumer {
	
	private int value;
	
	@Override
	public void accept(int value) {
		assertEquals(this.value++, value);
	}
	
	
	
}
