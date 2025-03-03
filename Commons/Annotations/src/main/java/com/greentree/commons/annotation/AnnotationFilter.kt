package com.greentree.commons.annotation

import java.lang.reflect.AnnotatedElement
import kotlin.reflect.KAnnotatedElement

interface AnnotationFilter {

	interface Chain {

		object Null : Chain {

			override fun filter(element: AnnotatedElement) = element
		}

		fun filter(element: AnnotatedElement): AnnotatedElement
	}

	fun filter(chain: Chain, element: AnnotatedElement): AnnotatedElement
}

fun AnnotationFilter.toChain(chain: AnnotationFilter.Chain = AnnotationFilter.Chain.Null) =
	object : AnnotationFilter.Chain {
		override fun filter(element: AnnotatedElement) = filter(chain, element)
	}

