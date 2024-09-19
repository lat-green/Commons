package com.greentree.commons.serialization.serializer

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.serialization.annotation.CustomSerializer
import com.greentree.commons.serialization.descriptor.SerialDescriptor
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

interface Serializer<T : Any> : SerializationStrategy<T>, DeserializationStrategy<T> {

	override val descriptor: SerialDescriptor
}

fun <T : Any> serializerFinal(cls: Class<T>) = serializerFinal(cls.kotlin)
inline fun <reified T : Any> serializerFinal() = serializerFinal(T::class)
fun <T : Any> serializerFinal(cls: KClass<T>): Serializer<T> {
	val origin = serializer(cls)
	if(origin is BaseClassSerializer)
		return if(ClassUtil.isExtends(java.util.Collection::class.java, cls.java))
			FinalCollectionClassSerializer(cls.java)
		else
			FinalClassSerializer(cls.java)
	return origin
}

inline fun <reified T : Any> serializer() = serializer(T::class)

fun <T : Any> serializer(cls: Class<T>) = serializer(cls.kotlin)

object SerializerCache {

	fun <T : Any> newInstance(cls: KClass<T>): T {
		return cls.objectInstance ?: run {
			val constructor = cls.java.getDeclaredConstructor()
			constructor.trySetAccessible()
			val instance = constructor.newInstance()
			instance
		}
	}
}

fun <T : Any> serializer(cls: KClass<T>): Serializer<T> =
	when {
		cls.java.isAnnotationPresent(CustomSerializer::class.java) -> SerializerCache.newInstance(
			cls.java.getDeclaredAnnotation(CustomSerializer::class.java)
				.serializator
		) as Serializer<T>

		cls.java.isEnum -> EnumSerializer(cls)
		cls == String::class -> StringSerializer
		cls == Boolean::class -> BooleanSerializer
		cls == Byte::class -> ByteSerializer
		cls == Char::class -> CharSerializer
		cls == Short::class -> ShortSerializer
		cls == Int::class -> IntSerializer
		cls == Long::class -> LongSerializer
		cls == Float::class -> FloatSerializer
		cls == Double::class -> DoubleSerializer
		cls == java.lang.Long::class -> LongSerializer
		cls == java.lang.Integer::class -> IntSerializer
		cls == java.lang.Short::class -> ShortSerializer
		cls == java.lang.Character::class -> CharSerializer
		cls == java.lang.Byte::class -> ByteSerializer
		cls == java.lang.Double::class -> DoubleSerializer
		cls == java.lang.Float::class -> FloatSerializer
		cls == java.lang.Boolean::class -> BooleanSerializer
		cls == java.lang.String::class -> StringSerializer
		isKotlin(cls) && cls.objectInstance != null -> KotlinObjectSerializer(cls)
		ClassUtil.isExtends(Collection::class.java, cls::class.java) && Modifier.isFinal(cls.java.modifiers) ->
			FinalCollectionClassSerializer(cls.java)

		Modifier.isFinal(cls.java.modifiers) -> FinalClassSerializer(cls.java)
		else -> BaseClassSerializer(cls.java)
	} as Serializer<T>

fun isKotlin(cls: KClass<*>): Boolean {
	return true
}

val Byte.Companion.VALUES
	get() = MAX_VALUE - MIN_VALUE + 1