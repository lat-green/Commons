package com.greentree.commons.serialization.format

data class BitsPackDecoderStructure(
	val origin: StructureFieldGroup<Decoder>,
) : StructureFieldGroup<Decoder> by origin {

	override fun field(name: String) = BitsPackDecoder(origin.field(name))
}

data class BitsPackDecoderCollection(
	val origin: CollectionFieldGroup<Decoder>,
) : CollectionFieldGroup<Decoder> by origin {

	override fun field(index: Int) = BitsPackDecoder(origin.field(index))
}
