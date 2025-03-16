package com.greentree.commons.serialization.serializator

interface EnumSerializator<E : Enum<E>> : Serializator<E> {

	override val type: Class<E>
}