package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

public class XAnalysisFunction1 implements AnalysisFunction1 {

	public static final XAnalysisFunction1 X = new XAnalysisFunction1();
	
	private XAnalysisFunction1() {
	}
	
	@Override
	public AnalysisFunction1 arcfunc() {
		return this;
	}
	
	@Override
	public float execute(float x) {
		return x;
	}

	@Override
	public boolean isBracketsNeeded() {
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("x");
		return builder.toString();
	}

	@Override
	public AnalysisFunction1 derivative() {
		return value(1);
	}

	@Override
	public AnalysisFunction1 antiderivative() {
		return mult(2, power(this, 2));
	}

}
