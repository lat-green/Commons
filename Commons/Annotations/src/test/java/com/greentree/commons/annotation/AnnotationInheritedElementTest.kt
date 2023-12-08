package com.greentree.commons.annotation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AnnotationInheritedElementTest {

	private val chain = AnnotationInheritedFilter.toChain()

	@Test
	fun getDeclaredAnnotationsByType_Role() {
		val cls = Person::class.java
		val annotationType = Role::class.java
		val annotations = chain.filter(cls).getDeclaredAnnotationsByType(annotationType)
		assertArrayEquals(annotations, arrayOf(Role("AAA")))
	}

	@Test
	fun getDeclaredAnnotationsByType_Name() {
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
		assertTrue(Role("AAA") in annotations)
	}

	@Test
	fun annotations() {
		val cls = Person::class.java
		val annotations = chain.filter(cls).annotations
		assertTrue(Name("BBB") in annotations)
		assertTrue(Role("AAA") in annotations)
	}
}
