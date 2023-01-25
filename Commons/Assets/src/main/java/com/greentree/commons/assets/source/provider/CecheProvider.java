package com.greentree.commons.assets.source.provider;

import java.util.function.Consumer;

import com.greentree.commons.assets.source.Source;

public class CecheProvider<T> implements SourceProvider<T> {
	
	private final SourceProvider<T> provider;
	
	private transient T ceche;
	
	public static <T> SourceProvider<T> ceche(Source<T> source) {
		final var provider = source.openProvider();
		if(provider.hasConst())
			return provider;
		return new CecheProvider<>(provider);
	}
	
	@Override
	public void close() {
		provider.close();
	}
	
	private CecheProvider(SourceProvider<T> provider) {
		this.provider = provider;
	}
	
	@Override
	public int characteristics() {
		return provider.characteristics();
	}
	
	@Override
	public T getOld() {
		return ceche;
	}
	
	@Override
	public boolean tryGet(Consumer<? super T> action) {
		return provider.tryGet(c-> {
			ceche = c;
			action.accept(ceche);
		});
	}
	
	@Override
	public T get() {
		provider.tryGet(c-> {
			ceche = c;
		});
		return ceche;
	}
	
	@Override
	public boolean isChenge() {
		return provider.isChenge();
	}
	
}
