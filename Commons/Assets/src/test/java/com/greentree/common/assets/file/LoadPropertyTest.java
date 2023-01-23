package com.greentree.common.assets.file;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.assets.serializator.LoadProperty;

public class LoadPropertyTest {
	
	static Stream<Array<LoadProperty>> arrays() {
		final var properties = Arrays.asList(LoadProperty.values());
		final var result = getBoolean(properties);
		return result.stream().map(arr->new Array<>(arr.toArray(new LoadProperty[arr.size()])));
	}
	
	private static <T> Collection<List<T>> getBoolean(Collection<T> collection) {
		final var list = new ArrayList<>(collection);
		return switch(list.size()) {
			case 0 -> Arrays.asList(new ArrayList<>());
			default -> {
				final var head = list.remove(0);
				final var tail = getBoolean(list);
				final var result = new ArrayList<List<T>>();
				for(var t : tail) {
					final var nl = new ArrayList<>(t);
					nl.add(head);
					result.add(t);
					result.add(nl);
				}
				yield result;
			}
		};
	}
	
	@MethodSource("arrays")
	@ParameterizedTest
	void LoadProperty_has(Array<LoadProperty> propertiesArray) {
		final var properties = propertiesArray.array;
		final var mask = LoadProperty.getMask(properties);
		for(var p : properties)
			assertTrue(LoadProperty.has(mask, p), mask + " not has " + p);
	}
	
	@MethodSource("arrays")
	@ParameterizedTest
	void LoadProperty_not_has(Array<LoadProperty> propertiesArray) {
		final var properties = propertiesArray.array;
		final var mask = LoadProperty.getMask(properties);
		final var not_mask = ~mask;
		for(var p : properties)
			assertFalse(LoadProperty.has(not_mask, p), mask + " has " + p);
	}
	
	private static final class Array<T> {
		
		public final T[] array;
		
		public Array(T[] array) {
			this.array = array;
		}
		
	}
	
}
