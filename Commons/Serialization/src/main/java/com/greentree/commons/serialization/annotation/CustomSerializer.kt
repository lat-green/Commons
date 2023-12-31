package com.greentree.commons.serialization.annotation

import com.greentree.commons.serialization.serializer.Serializer
import kotlin.reflect.KClass

annotation class CustomSerializer(
	val serializator: KClass<out Serializer<*>>,
)
