package com.greentree.commons.assets.source.provider;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.function.Source1Function;


public class SourceFunctionMapProvider<IN, OUT> extends AbstractMapProvider<IN, OUT> {
	
	public static final int CHARACTERISTICS = 0;
	private final Source1Function<? super IN, OUT> function;
	
	public SourceFunctionMapProvider(Source<IN> source, Source1Function<? super IN, OUT> function) {
		super(source);
		this.function = function;
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	protected OUT map(IN in) {
		return function.apply(in);
	}
	
}
