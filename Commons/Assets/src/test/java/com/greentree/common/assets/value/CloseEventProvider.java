package com.greentree.common.assets.value;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;


public class CloseEventProvider<T> implements SourceProvider<T> {
	
	private final SourceProvider<T> provider;
	private final Runnable onClose;
	
	public CloseEventProvider(Source<T> source, Runnable onClose) {
		this.provider = source.openProvider();
		this.onClose = onClose;
	}
	
	@Override
	public int characteristics() {
		return provider.characteristics() | ~BLANCK_CLOSE;
	}
	
	@Override
	public T get() {
		return provider.get();
	}
	
	@Override
	public void close() {
		onClose.run();
		provider.close();
	}
	
	@Override
	public boolean isChenge() {
		return provider.isChenge();
	}
	
}
