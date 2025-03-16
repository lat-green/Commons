package com.greentree.commons.serialization.serializator

import com.greentree.commons.serialization.context.SerializationContext
import com.greentree.commons.serialization.format.Decoder
import com.greentree.commons.serialization.format.Encoder
import com.greentree.commons.serialization.serializator.provider.SerializatorProvider

data class EnumAsByteSerializator<E : Enum<E>>(
	override val type: Class<E>,
) : EnumSerializator<E> {

	override fun serialize(context: SerializationContext, encoder: Encoder, value: E) =
		encoder.encodeByte(value.ordinal.toByte())

	override fun deserialize(context: SerializationContext, decoder: Decoder): E =
		type.enumConstants[decoder.decodeByte().toInt()]

	companion object : SerializatorProvider {

		override fun <T : Any> provide(cls: Class<T>): Serializator<T>? {
			if(!cls.isEnum)
				return null
			return provideEnum(cls as Class<out Enum<*>>) as Serializator<T>
		}

		private fun <T : Enum<T>> provideEnum(cls: Class<T>): Serializator<T> {
			val enumConstantSize = cls.enumConstants.size
			return if(enumConstantSize.toUByte().toInt() == enumConstantSize)
				EnumAsByteSerializator(cls)
			else
				EnumAsIntSerializator(cls)
		}
	}
}