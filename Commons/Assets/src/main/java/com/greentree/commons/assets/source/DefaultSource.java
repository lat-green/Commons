package com.greentree.commons.assets.source;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.greentree.commons.assets.source.provider.DefultProvider;
import com.greentree.commons.assets.source.provider.SourceProvider;

public final class DefaultSource<T> implements Source<T> {
	
	private static final long serialVersionUID = 1L;
	private final Iterable<? extends Source<? extends T>> values;
	
	private DefaultSource(Iterable<? extends Source<? extends T>> values) {
		this.values = values;
	}
	
	public static <T> Source<T> newValue(Iterable<? extends Source<T>> values) {
		return newValue(StreamSupport.stream(values.spliterator(), false));
	}
	
	@SafeVarargs
	public static <T> Source<T> newValue(Source<T>... values) {
		return newValue(Stream.of(values));
	}
	
	public static <T> Source<T> newValue(Stream<? extends Source<T>> values) {
		final var list = values.unordered().distinct().filter(s->!(s.hasConst() && s.isNull()))
				.toList();
		if(list.isEmpty())
			return NullSource.instance();
		if(list.size() == 1)
			return list.iterator().next();
		for(var v : list) {
			if(!v.hasConst())
				break;
			if(!v.isNull())
				return v;
		}
		return new DefaultSource<>(list);
	}
	
	@Override
	public int characteristics() {
		return DefultProvider.CHARACTERISTICS;
	}
	
	@Override
	public SourceProvider<T> openProvider() {
		return new DefultProvider<>(values);
	}
	
}
