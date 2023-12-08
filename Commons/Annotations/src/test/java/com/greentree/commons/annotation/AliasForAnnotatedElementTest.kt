package com.greentree.commons.annotation

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AliasForAnnotatedElementTest {

	private val chain = AliasForFilter.toChain(AnnotationInheritedFilter.toChain())

	@Test
	fun getDeclaredAnnotation_Role() {
		val cls = Person::class.java
		val annotationType = Role::class.java
		val role = chain.filter(cls).getDeclaredAnnotation(annotationType)
		assertEquals(role, Role("BBB"))
	}

	@Test
	fun getAnnotation_Role() {
		val cls = Person::class.java
		val annotationType = Role::class.java
		val annotations = chain.filter(cls).getAnnotation(annotationType)
		assertEquals(annotations, Role("BBB"))
	}

	@Test
	fun getDeclaredAnnotation_Name() {
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
		assertTrue(Name("BBB") in annotations, "${annotations.contentToString()}")
	}
}