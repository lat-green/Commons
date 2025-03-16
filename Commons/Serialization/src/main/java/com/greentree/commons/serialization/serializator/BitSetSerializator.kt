package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import java.util.*

data object BitSetSerializator : Serializator<BitSet> {

	override fun deserialize(context: SerializationContext, decoder: Decoder): BitSet {
		decoder.beginCollection().use { collection ->
			val length = collection.field(0).use {
				it.decodeInt()
			}
			collection.field(1).use {
				return it.decodeBits(length)
			}
		}
	}

	override fun serialize(context: SerializationContext, encoder: Encoder, value: BitSet) {
		encoder.beginCollection().use { collection ->
			collection.field(0).use {
				it.encodeInt(value.length())
			}
			collection.field(1).use {
				it.encodeBits(value)
			}
		}
	}
}