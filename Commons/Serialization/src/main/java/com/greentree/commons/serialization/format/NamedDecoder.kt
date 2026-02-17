package com.greentree.commons.serialization.format

interface NamedDecoder : Decoder {

	override fun beginStructure(): NamedStructureFieldGroup<Decoder>
	override fun beginCollection(): NamedCollectionFieldGroup<Decoder>
}