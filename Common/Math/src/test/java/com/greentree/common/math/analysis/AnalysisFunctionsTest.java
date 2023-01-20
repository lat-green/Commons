package com.greentree.common.math.analysis;

import org.junit.jupiter.api.Test;


import static com.greentree.common.math.analysis.AnalysisFunctions.*;

public class AnalysisFunctionsTest {

	@Test
	void test1() {
		final var func = power(X, 2f / 3);
		System.out.println(func);
		System.out.println(func.derivative());
		System.out.println(func.antiderivative());
		System.out.println(func.arcfunc());
	}
	
}
