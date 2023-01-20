package com.greentree.common.math.analysis;


public class LogFunction1 implements AnalysisFunction1 {

	private final AnalysisFunction1 a, b;
	
	public LogFunction1(AnalysisFunction1 a, AnalysisFunction1 b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("log[");
		builder.append(a);
		builder.append("](");
		builder.append(b);
		builder.append(")");
		return builder.toString();
	}

	@Override
	public float execute(float x) {
		return (float) (Math.log(b.execute(x)) / Math.log(a.execute(x)));
	}

	public static AnalysisFunction1 create(AnalysisFunction1 a, AnalysisFunction1 b) {
		return new LogFunction1(a, b);
	}

}
