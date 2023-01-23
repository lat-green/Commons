package com.greentree.commons.assets.source.mapper;

import com.greentree.commons.assets.source.MutableSource;
import com.greentree.commons.assets.source.provider.SourceProvider;

public abstract class AbstractMapSource<IN, OUT> implements Mapper<IN, OUT> {
	
	private static final long serialVersionUID = 1L;
	
	private final Mapper<OUT, OUT> result;
	
	public AbstractMapSource() {
		this(new MutableSource<>());
	}
	
	public AbstractMapSource(Mapper<OUT, OUT> result) {
		this.result = result;
	}
	
	@Override
	public void set(IN in) {
		synchronized(this) {
			var value = result.get();
			try {
				value = map0(in, value);
			}catch(RuntimeException e) {
				e.printStackTrace();
				return;
			}
			result.set(value);
		}
	}
	
	private OUT map0(IN in, OUT oldValue) {
		if(oldValue == null)
			return map(in);
		else
			return map(in, oldValue);
	}
	
	protected abstract OUT map(IN in);
	
	protected abstract OUT map(IN in, OUT oldValue);
	
	@Override
	public SourceProvider<OUT> openProvider() {
		return result.openProvider();
	}
	
	@Override
	public int characteristics() {
		return 0;
	}
	
}
