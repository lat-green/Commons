package com.greentree.commons.annotation

import java.lang.reflect.AnnotatedElement

object AnnotationInheritedFilter : DecoratorAnnotationFilter {

	override fun decorator(element: AnnotatedElement) = AnnotationInheritedElement(element)
}