package com.greentree.commons.serialization.serializator.type

import com.greentree.commons.graph.tree
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.context.manager
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.accuracy.IntAccuracy
import com.greentree.commons.serialization.serializator.manager.serializator

data object KotlinSealedTypeSerializator : TypedSerializator {

	override fun isSupported(guaranteed: Class<*>) = guaranteed.kotlin.isSealed

	override fun serialize(context: SerializationContext, encoder: Encoder, guaranteed: Class<*>, current: Class<*>) {
		val manager = context.manager
		val sealedSubclasses = tree(guaranteed.kotlin) {
			yieldAll(it.sealedSubclasses)
		}.map { it.java }
		val indexAccuracy = IntAccuracy.Property(IntAccuracy(0, sealedSubclasses.size))
		val index = sealedSubclasses.indexOf(current)
		manager.serializator<Int>()
			.serialize(context + indexAccuracy, encoder, index)
	}

	override fun deserialize(context: SerializationContext, decoder: Decoder, guaranteed: Class<*>): Class<*> {
		val manager = context.manager
		val sealedSubclasses = tree(guaranteed.kotlin) {
			yieldAll(it.sealedSubclasses)
		}.map { it.java }
		val indexAccuracy = IntAccuracy.Property(IntAccuracy(0, sealedSubclasses.size))
		val index = manager.serializator<Int>()
			.deserialize(context + indexAccuracy, decoder)
		return sealedSubclasses[index]
	}
}