package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider

data class EnumAsByteSerializator<E : Enum<E>>(
	override val type: TypeInfo<E>,
) : EnumSerializator<E> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: E) =
		encoder.encodeByte(value.ordinal.toByte())

	override fun deserialize(context: SerializationContext, decoder: Decoder): E =
		type.toClass().enumConstants[decoder.decodeByte().toInt()]

	companion object : SerializatorProvider {

		override fun <T : Any> provide(type: TypeInfo<out T>): Serializator<T>? {
			val cls = type.toClass()
			if(!cls.isEnum)
				return null
			return provideEnum(type as TypeInfo<out Enum<*>>) as Serializator<T>
		}

		private fun <T : Enum<T>> provideEnum(type: TypeInfo<T>): Serializator<T> {
			val enumConstantSize = type.toClass().enumConstants.size
			return if(enumConstantSize.toUByte().toInt() == enumConstantSize)
				EnumAsByteSerializator(type)
			else
				EnumAsIntSerializator(type)
		}
	}
}