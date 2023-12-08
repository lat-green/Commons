package com.greentree.commons.annotation

import java.lang.reflect.AnnotatedElement

object AliasForFilter : DecoratorAnnotationFilter {

	override fun decorator(element: AnnotatedElement) = AliasForAnnotatedElement(element)
}