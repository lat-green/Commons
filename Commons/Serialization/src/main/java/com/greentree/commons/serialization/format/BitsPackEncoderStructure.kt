package com.greentree.commons.serialization.format

data class BitsPackEncoderStructure(
	val origin: StructureFieldGroup<Encoder>,
) : StructureFieldGroup<Encoder> by origin {

	override fun field(name: String) = BitsPackEncoder(origin.field(name))
}

data class BitsPackEncoderCollection(
	val origin: CollectionFieldGroup<Encoder>,
) : CollectionFieldGroup<Encoder> by origin {

	override fun field(index: Int) = BitsPackEncoder(origin.field(index))
}
