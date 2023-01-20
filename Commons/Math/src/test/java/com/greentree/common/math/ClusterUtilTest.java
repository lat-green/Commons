package com.greentree.common.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.iterator.IteratorUtil;

public class ClusterUtilTest {
	
	@Test
	void test1() {
		final var res = iterable(-80, -70, -50, 1.1, 1.5, 2, 5, 8, 99, 101, 106, 500, 504, 456);
		
		final var cs = ClusterUtil.clusterDBSCAN(res, (a, b) -> Math.abs(a - b));
		
		assertEquals(IteratorUtil.size(cs), 4);
		
//		for(var s : cs)
//			System.out.println(IteratorUtil.toString(s));
	}

	private Iterable<Double> iterable(Number...ns) {
		final var fs = new Double[ns.length];
		for(int i = 0; i < ns.length; i++) fs[i] = ns[i].doubleValue();
		return IteratorUtil.iterable(fs);
	}
	
}
