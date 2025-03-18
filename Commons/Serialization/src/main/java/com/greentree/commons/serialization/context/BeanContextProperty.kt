package com.greentree.commons.serialization.context

import com.greentree.commons.context.BeanContext

data class BeanContextProperty(override val value: BeanContext) : SerializationContext.Property<BeanContext> {

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<BeanContextProperty>
}