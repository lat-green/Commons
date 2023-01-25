package com.greentree.commons.assets.source.merge;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.source.provider.SourceProvider;


public final class MIProvider<T> implements SourceProvider<Iterable<T>> {
	
	public static final int CHARACTERISTICS = NOT_NULL;
	private final Collection<SourceProvider<? extends T>> providers;
	
	public MIProvider(Iterable<? extends Source<? extends T>> sources) {
		providers = new ArrayList<>();
		for(var s : sources)
			providers.add(s.openProvider());
	}
	
	@Override
	public int characteristics() {
		return CHARACTERISTICS;
	}
	
	@Override
	public void close() {
		for(var p : providers)
			p.close();
	}
	
	@Override
	public Iterable<T> getOld() {
		final var result = new ArrayList<T>();
		for(var p : providers)
			result.add(p.getOld());
		return result;
	}
	
	@Override
	public Iterable<T> get() {
		final var result = new ArrayList<T>();
		for(var p : providers)
			result.add(p.get());
		return result;
	}
	
	@Override
	public boolean isChenge() {
		for(var p : providers)
			if(p.isChenge())
				return p.isChenge();
		return false;
	}
	
	@Override
	public String toString() {
		return "MIProvider " + providers;
	}
	
}
