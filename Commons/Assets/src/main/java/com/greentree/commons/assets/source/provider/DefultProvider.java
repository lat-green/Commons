package com.greentree.commons.assets.source.provider;

import java.util.ArrayList;
import java.util.List;

import com.greentree.commons.assets.source.Source;


public final class DefultProvider<T> implements SourceProvider<T> {
	
	public static final int CHARACTERISTICS = 0;
	private final List<SourceProvider<? extends T>> providers;
	private int index;
	
	public DefultProvider(Iterable<? extends Source<? extends T>> values) {
		this.providers = new ArrayList<>();
		for(var s : values)
			providers.add(s.openProvider());
		init();
	}
	
	private void init() {
		index = 0;
		while(index < providers.size()) {
			final var s = providers.get(index);
			if(!s.isNull())
				return;
			index++;
		}
	}
	
	@Override
	public void close() {
		for(var s : providers)
			s.close();
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public boolean isChenge() {
		for(int i = 0; i < index; i++)
			if(providers.get(index).isChenge())
				return true;
		return false;
	}
	
	@Override
	public T get() {
		init();
		return providers.get(index).get();
	}
	
}
