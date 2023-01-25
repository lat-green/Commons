package com.greentree.commons.assets.source.store;

public abstract class AbstractMapStore<IN, OUT> implements Store<IN> {
	
	private static final long serialVersionUID = 1L;
	
	protected final Store<OUT> output;
	
	public AbstractMapStore(Store<OUT> output) {
		this.output = output;
	}
	
	@Override
	public void set(IN value) {
		output.set(map(value));
	}
	
	protected abstract OUT map(IN in);
	
}
