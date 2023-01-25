package com.greentree.common.assets.value;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;


public class CloseEventValue<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	private final Runnable onClose;
	private final Source<T> source;
	
	public CloseEventValue(Source<T> source, Runnable onClose) {
		this.source = source;
		this.onClose = onClose;
	}
	
	@Override
	public int characteristics() {
		return 0;
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new CloseEventProvider<>(source, onClose);
	}
	
}
