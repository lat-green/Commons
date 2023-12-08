package com.greentree.commons.annotation

import java.lang.reflect.AnnotatedElement

data class AnnotationInheritedElement(
	private val origin: AnnotatedElement,
) : AnnotatedElement {

	override fun <T : Annotation?> getAnnotation(annotationClass: Class<T>) =
		annotations.filterIsInstance(annotationClass).firstOrNull()

	override fun getAnnotations() =
		origin.annotations.flatMap { getAnnotationWithInherited(it) }.toTypedArray()

	override fun getDeclaredAnnotations() =
		origin.declaredAnnotations.flatMap { getAnnotationWithInherited(it) }.toTypedArray()

	private fun getAnnotationWithInherited(annotation: Annotation): Iterable<Annotation> {
		val inherited = getAnnotationInherited(annotation)
		return listOf(annotation) + inherited.flatMap { getAnnotationWithInherited(it) }
	}

	private fun getAnnotationInherited(annotation: Annotation) = annotation.annotationClass.java.annotations.filter {
		it.annotationClass.java.isAnnotationPresent(
			AnnotationInherited::class.java
		)
	}
}
