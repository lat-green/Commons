package com.greentree.common.math.analysis;


public interface AnalysisFunction1 {
	
	float execute(float x);
	
	default boolean isBracketsNeeded() {
		return true;
	}
    
	default AnalysisFunction1 derivative() {
		throw new UnsupportedOperationException("derivative " + this);
	}
	default AnalysisFunction1 antiderivative() {
		throw new UnsupportedOperationException("antiderivative " + this);
	}
	default AnalysisFunction1 arcfunc() {
		throw new UnsupportedOperationException("arcfunc " + this);
	}
}
