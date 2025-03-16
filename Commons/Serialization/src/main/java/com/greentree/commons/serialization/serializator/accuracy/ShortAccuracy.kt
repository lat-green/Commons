package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.serialization.context.SerializationContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@AnnotationInherited
@MustBeDocumented
annotation class ShortAccuracy(
	val min: Short,
	val max: Short,
	val accuracy: Short = 1,
) {

	data class Property(
		override val value: ShortAccuracy,
	) : SerializationContext.Property<ShortAccuracy> {

		override val key
			get() = Key

		companion object Key : SerializationContext.Key<Property>
	}

	data class Calculator(
		val min: Short,
		val max: Short,
		val accuracy: Short,
	) : AccuracyCalculator<Short> {

		override val countValues
			get() = ((max - min) / accuracy).toLong()

		override fun encodeValue(value: Short) = ((value - min) / accuracy).toLong()

		override fun decodeValue(value: Long) = (value * accuracy + min).toShort()
	}
}

val ShortAccuracy.calculator
	get() = ShortAccuracy.Calculator(min, max, accuracy)