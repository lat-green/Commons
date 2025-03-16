package com.greentree.commons.serialization.format

data class BitsPackEncoderStructure(
	val origin: Structure<Encoder>
) : Structure<Encoder> by origin {

	override fun field(index: Int) = BitsPackEncoder(origin.field(index))
	override fun field(name: String) = BitsPackEncoder(origin.field(name))
}
