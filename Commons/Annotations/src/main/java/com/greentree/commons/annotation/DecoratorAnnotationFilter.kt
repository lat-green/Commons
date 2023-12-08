package com.greentree.commons.annotation

import java.lang.reflect.AnnotatedElement

interface DecoratorAnnotationFilter : AnnotationFilter {

	override fun filter(chain: AnnotationFilter.Chain, element: AnnotatedElement) = decorator(chain.filter(element))

	fun decorator(element: AnnotatedElement): AnnotatedElement
}