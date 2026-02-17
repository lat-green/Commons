package com.greentree.commons.serialization.format

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

interface NamedDecoder : Decoder {

	override fun beginStructure(): NamedStructureFieldGroup<NamedDecoder>
	override fun beginCollection(): NamedCollectionFieldGroup<NamedDecoder>
}

@OptIn(ExperimentalContracts::class)
inline fun <R> NamedDecoder.beginSizedCollection(block: (size: Int, NamedCollectionFieldGroup<NamedDecoder>) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	beginCollection().use { c ->
		return block(c.size, c)
	}
}