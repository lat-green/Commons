package com.greentree.commons.serialization.format

data class BitsPackDecoderStructure(
	val origin: Structure<Decoder>
) : Structure<Decoder> by origin {

	override fun field(index: Int) = BitsPackDecoder(origin.field(index))
	override fun field(name: String) = BitsPackDecoder(origin.field(name))
}
