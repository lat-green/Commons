package com.greentree.commons.serialization.serializator.manager

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.EmptySerializationContext
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.Serializator
import kotlin.Deprecated
import kotlin.ReplaceWith
import kotlin.reflect.KClass

interface SerializatorManager : SerializationContext.Element {

	override val key
		get() = Key

	companion object Key : SerializationContext.Key<SerializatorManager>

	fun <T : Any> serializator(guaranteed: TypeInfo<out T>): Serializator<T>
	fun <T : Any> realSerializator(cls: TypeInfo<T>): Serializator<T>
}

@Deprecated("", ReplaceWith("serializator(TypeInfo(guaranteed))"))
fun <T : Any> SerializatorManager.serializator(guaranteed: Class<out T>): Serializator<T> =
	serializator(TypeInfo(guaranteed))

@Deprecated("", ReplaceWith("realSerializator(TypeInfo(cls))"))
fun <T : Any> SerializatorManager.realSerializator(cls: Class<T>): Serializator<T> =
	realSerializator(TypeInfo(cls))

fun <T : Any> SerializatorManager.serializator(guaranteed: KClass<out T>) = serializator(guaranteed.java)
inline fun <reified T : Any> SerializatorManager.serializator() = serializator(T::class.java)

inline fun <reified T : Any> SerializatorManager.deserialize(context: SerializationContext, decoder: Decoder): T =
	serializator<T>().deserialize(context, decoder)

inline fun <reified T : Any> SerializatorManager.serialize(context: SerializationContext, encoder: Encoder, value: T) =
	serializator<T>().serialize(context, encoder, value)

inline fun <reified T : Any> SerializatorManager.deserialize(decoder: Decoder): T =
	deserialize(EmptySerializationContext, decoder)

inline fun <reified T : Any> SerializatorManager.serialize(encoder: Encoder, value: T) =
	serialize(EmptySerializationContext, encoder, value)