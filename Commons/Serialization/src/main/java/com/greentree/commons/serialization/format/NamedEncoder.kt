package com.greentree.commons.serialization.format

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface NamedEncoder : Encoder {

	override fun beginStructure(): StructureFieldGroup<NamedEncoder>
	override fun beginCollection(): CollectionFieldGroup<NamedEncoder>
}

@OptIn(ExperimentalContracts::class)
inline fun <R> NamedEncoder.beginSizedCollection(block: (CollectionFieldGroup<NamedEncoder>) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	beginCollection().use { c ->
		return block(c)
	}
}