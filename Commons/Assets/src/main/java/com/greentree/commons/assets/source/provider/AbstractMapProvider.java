package com.greentree.commons.assets.source.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.source.Source;

public abstract class AbstractMapProvider<IN, OUT> implements SourceProvider<OUT> {
	
	protected final SourceProvider<IN> input;
	
	public AbstractMapProvider(Source<IN> input) {
		this.input = input.openProvider();
	}
	
	@Override
	public void close() {
		input.close();
	}
	
	@Override
	public boolean tryGet(Consumer<? super OUT> action) {
		return input.tryGet(v-> {
			action.accept(map(v));
		});
	}
	
	@Override
	public boolean isChenge() {
		return input.isChenge();
	}
	
	@Override
	public OUT get() {
		final var v = input.get();
		return map(v);
	}
	
	protected abstract OUT map(IN in);
	
}
