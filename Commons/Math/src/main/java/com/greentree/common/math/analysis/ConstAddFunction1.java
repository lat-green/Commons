package com.greentree.common.math.analysis;

import static com.greentree.common.math.analysis.AnalysisFunctions.*;

import com.greentree.common.math.Mathf;

public class ConstAddFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 func;
	private final float m;

	private ConstAddFunction1(AnalysisFunction1 func, float m) {
		this.func = func;
		this.m = m;
	}

	@Override
	public AnalysisFunction1 antiderivative() {
		return add(func.antiderivative(), mult(X, m));
	}

	@Override
	public AnalysisFunction1 derivative() {
		return func.derivative();
	}

	@Override
	public float execute(float x) {
		return func.execute(x) + m;
	}


    @Override
    public AnalysisFunction1 arcfunc() {
    	return add(func.arcfunc(), -m);
    }
	
	public AnalysisFunction1 getFunc() {
		return func;
	}

	
	public float getAdded() {
		return m;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(func.isBracketsNeeded()) {
			builder.append("(");
			builder.append(func);
			builder.append(")");
		}else {
			builder.append(func);
		}
		if(m > 0)
			builder.append("+");
		builder.append(m);
		return builder.toString();
	}
	
	public static AnalysisFunction1 create(AnalysisFunction1 a, float b) {
		if(Mathf.equals(b, 0)) 
			return a;
		if(a instanceof ValueFunction1) {
			final var v = ((ValueFunction1) a).getValue();
			return add(v, b);
		}
		return new ConstAddFunction1(a, b);
	}


}
