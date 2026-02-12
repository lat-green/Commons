package com.greentree.commons.serialization.serializator

import com.greentree.commons.reflection.info.TypeInfo

interface EnumSerializator<E : Enum<E>> : Serializator<E> {

	override val type: TypeInfo<E>
}