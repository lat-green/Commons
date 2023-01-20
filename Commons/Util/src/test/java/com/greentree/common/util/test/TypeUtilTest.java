package com.greentree.common.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.commons.util.cortege.Pair;

public class TypeUtilTest {

	static Stream<TypeInfo<? extends Comparable<?>>> comparables() {
		return Stream.of(Integer.class, Float.class, File.class).map(cls -> TypeInfoBuilder.getTypeInfo(cls));
	}
	
	static Stream<Pair<TypeInfo<?>, TypeInfo<?>>> collections() {
		return Stream.of(
				TypeInfoBuilder.getTypeInfo(ArrayList.class),
				TypeInfoBuilder.getTypeInfo(ArrayList.class, ArrayList.class),
				TypeInfoBuilder.getTypeInfo(ArrayList.class, TypeInfoBuilder.getTypeInfo(ArrayList.class, ArrayList.class))
		).map(t -> new Pair<>(TypeInfoBuilder.getTypeInfo(ArrayList.class, t), t));
	}

	@MethodSource("comparables")
	@ParameterizedTest
	<T extends Comparable<T>> void getType_Comparable(TypeInfo<T> type) {
		final var actual_type = TypeUtil.getFirstAtgument(type, Comparable.class);
		assertEquals(type, actual_type);
	}
	
	@MethodSource("collections")
	@ParameterizedTest
	<T> void getType_Collections(Pair<TypeInfo<? extends Collection<T>>, TypeInfo<T>> type) {
		final var actual_type = TypeUtil.getFirstAtgument(type.first, Collection.class);
		assertEquals(type.seconde, actual_type);
	}
	
	@Test
	void lca_Integer_Float() {
		final var typeA = TypeInfoBuilder.getTypeInfo(Integer.class);
		final var typeB = TypeInfoBuilder.getTypeInfo(Float.class);
		final var typeC = TypeInfoBuilder.getTypeInfo(Number.class);
		
		final var typeLCA = TypeUtil.lca(typeA, typeB);
		
		assertEquals(typeC, typeLCA);
	}

	@Test
	void lca_Integer_ArrayList() {
		final var typeA = TypeInfoBuilder.getTypeInfo(Integer.class);
		final var typeB = TypeInfoBuilder.getTypeInfo(ArrayList.class);
		final var typeC = TypeInfoBuilder.getTypeInfo(Serializable.class);
		
		final var typeLCA = TypeUtil.lca(typeA, typeB);
		
		assertEquals(typeC, typeLCA);
	}
	
	@Test
	void lca_HashSet_ArrayList() {
		final var typeA = TypeInfoBuilder.getTypeInfo(HashSet.class, String.class);
		final var typeB = TypeInfoBuilder.getTypeInfo(ArrayList.class, String.class);
		final var typeC = TypeInfoBuilder.getTypeInfo(AbstractCollection.class, String.class);
		
		final var typeLCA = TypeUtil.lca(typeA, typeB);
		
		assertEquals(typeC, typeLCA);
	}
	
}
