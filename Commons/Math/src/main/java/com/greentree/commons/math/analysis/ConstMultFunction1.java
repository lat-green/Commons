package com.greentree.commons.math.analysis;

import static com.greentree.commons.math.analysis.AnalysisFunctions.*;

import com.greentree.commons.math.Mathf;

public class ConstMultFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 func;
	private final float c;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(c);
		if(func.isBracketsNeeded()) {
			builder.append("(");
			builder.append(func);
			builder.append(")");
		}else {
			builder.append(func);
		}
		return builder.toString();
	}

	private ConstMultFunction1(float c, AnalysisFunction1 func) {
		this.func = func;
		this.c = c;
	}

	
	public AnalysisFunction1 getFunc() {
		return func;
	}

	
	public float getConst() {
		return c;
	}

	@Override
	public float execute(float x) {
		return c * func.execute(x);
	}

	@Override
	public AnalysisFunction1 derivative() {
		return mult(c, func.derivative());
	}

	@Override
	public AnalysisFunction1 antiderivative() {
		return mult(c, func.antiderivative());
	}

	public static AnalysisFunction1 create(float a, AnalysisFunction1 b) {
		if(Mathf.equals(a, 1)) 
			return b;
		if(b instanceof ValueFunction1) {
			final var v = ((ValueFunction1) b).getValue();
			return value(a * v);
		}
		if(b instanceof ConstMultFunction1) {
			final var mc = (ConstMultFunction1) b;
			return mult(a * mc.getConst(), mc.func);
		}
		if(b instanceof AddFunction1) {
			final var mc = (AddFunction1) b;
			return add(mult(a, mc.getA()), mult(a, mc.getB()));
		}
		return new ConstMultFunction1(a, b);
	}

	@Override
	public boolean isBracketsNeeded() {
		return false;
	}
	
	@Override
	public AnalysisFunction1 arcfunc() {
		return mult(1f / c, func.arcfunc());
	}
	
}
