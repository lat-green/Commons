package com.greentree.commons.serialization.serializator.manager

import com.greentree.commons.serialization.context.EmptySerializationContext
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import kotlin.reflect.KClass

interface SerializatorManager : SerializationContext.Element {

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<SerializatorManager>

	fun <T> serializator(guaranteed: Class<out T>): Serializator<T>
	fun <T> realSerializator(cls: Class<T>): Serializator<T>
}

fun <T : Any> SerializatorManager.serializator(guaranteed: KClass<out T>) = serializator(guaranteed.java)
inline fun <reified T> SerializatorManager.serializator() = serializator(T::class.java)

inline fun <reified T> SerializatorManager.deserialize(context: SerializationContext, decoder: Decoder): T =
	serializator<T>().deserialize(context, decoder)

inline fun <reified T> SerializatorManager.serialize(context: SerializationContext, encoder: Encoder, value: T) =
	serializator<T>().serialize(context, encoder, value)

inline fun <reified T> SerializatorManager.deserialize(decoder: Decoder): T =
	deserialize(EmptySerializationContext, decoder)

inline fun <reified T> SerializatorManager.serialize(encoder: Encoder, value: T) =
	serialize(EmptySerializationContext, encoder, value)