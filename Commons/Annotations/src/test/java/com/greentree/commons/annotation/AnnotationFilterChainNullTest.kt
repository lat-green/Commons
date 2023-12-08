package com.greentree.commons.annotation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AnnotationFilterChainNullTest {

	private val chain = AnnotationFilter.Chain.Null

	@Test
	fun getDeclaredAnnotationsByType() {
		val cls = Person::class.java
		val annotationType = Name::class.java
		val annotations = chain.filter(cls).getDeclaredAnnotationsByType(annotationType)
		assertArrayEquals(annotations, arrayOf(Name("BBB")))
	}

	@Test
	fun declaredAnnotations() {
		val cls = Person::class.java
		val annotations = chain.filter(cls).declaredAnnotations
		assertTrue(Name("BBB") in annotations)
	}

	@Test
	fun annotations() {
		val cls = Person::class.java
		val annotations = chain.filter(cls).annotations
		assertTrue(Name("BBB") in annotations)
	}
}