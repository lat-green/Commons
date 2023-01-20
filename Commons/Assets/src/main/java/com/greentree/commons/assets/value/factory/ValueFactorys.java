package com.greentree.commons.assets.value.factory;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

public final class ValueFactorys {
	
	public static final class Builder implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private ValueFactory RESULT = new DefaultValueFactory();
		
		public Builder addLazy() {
			RESULT = RESULT.andThen(new LazyValueFactory());
			return this;
		}
		
		public Builder addAsync(ExecutorService executor) {
			RESULT = RESULT.andThen(new AsyncValueFactory(executor));
			return this;
		}
		
		public ValueFactory build() {
			return RESULT;
		}
		
	}
	
	private ValueFactorys() {
	}
	
	public static ValueFactorys.Builder builder() {
		return new Builder();
	}
	
}
