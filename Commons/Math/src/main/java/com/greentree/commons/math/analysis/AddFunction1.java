package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

public class AddFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 a, b;

	private AddFunction1(AnalysisFunction1 a, AnalysisFunction1 b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public AnalysisFunction1 antiderivative() {
		return add(a.antiderivative(), b.antiderivative());
	}

	@Override
	public AnalysisFunction1 derivative() {
		return add(a.derivative(), b.derivative());
	}

	@Override
	public float execute(float x) {
		return a.execute(x) + b.execute(x);
	}

	public AnalysisFunction1 getA() {
		return a;
	}

	public AnalysisFunction1 getB() {
		return b;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(a.isBracketsNeeded()) {
			builder.append("(");
			builder.append(a);
			builder.append(")");
		}else {
			builder.append(a);
		}
		builder.append("+");
		if(b.isBracketsNeeded()) {
			builder.append("(");
			builder.append(b);
			builder.append(")");
		}else {
			builder.append(b);
		}
		return builder.toString();
	}
	
	public static AnalysisFunction1 create(AnalysisFunction1 a, AnalysisFunction1 b) {
		if(a instanceof ValueFunction1) {
			final var v = ((ValueFunction1) a).getValue();
			return add(v, b);
		}
		if(b instanceof ValueFunction1) {
			final var v = ((ValueFunction1) b).getValue();
			return add(v, a);
		}
		return new AddFunction1(a, b);
	}



}
