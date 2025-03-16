package com.greentree.commons.serialization.context

import java.lang.reflect.AnnotatedElement

data class AnnotatedElementProperty(
	override val value: AnnotatedElement,
) : SerializationContext.Property<AnnotatedElement> {

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<AnnotatedElementProperty>
}
