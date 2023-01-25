package com.greentree.common.assets.source;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.MutableSource;
import com.greentree.commons.assets.source.merge.M2Source;

public class SourceTest {
	
	private static final String TEXT_1 = "A";
	private static final String TEXT_2 = "B";
	private static final String TEXT_3 = "C";
	
	@Test
	void test1() {
		final var v1 = new MutableSource<>(TEXT_1);
		final var v2 = new MutableSource<>(TEXT_2);
		
		try(final var m = new M2Source<>(v1, v2).openProvider();) {
			assertEquals(m.get().t1(), TEXT_1);
			
			v1.set(TEXT_3);
			
			assertEquals(m.get().t1(), TEXT_3);
		}
	}
	
}
