package com.greentree.commons.annotation

import com.greentree.commons.annotation.AnnotationFilter.*
import java.lang.reflect.AnnotatedElement
import kotlin.reflect.KAnnotatedElement

data class KAnnotatedElementWrapper(val origin: KAnnotatedElement) : AnnotatedElement {

	override fun <T : Annotation> getAnnotation(annotationClass: Class<T>): T? =
		origin.annotations.firstOrNull { annotationClass.isInstance(it) } as T?

	override fun getAnnotations(): Array<Annotation> = origin.annotations.toTypedArray()

	override fun getDeclaredAnnotations(): Array<Annotation> = origin.annotations.toTypedArray()
}

fun KAnnotatedElement.asAnnotatedElement() = KAnnotatedElementWrapper(this)
fun AnnotationFilter.filter(chain: Chain, element: KAnnotatedElement) = filter(chain, element.asAnnotatedElement())
fun Chain.filter(element: KAnnotatedElement) = filter(element.asAnnotatedElement())
