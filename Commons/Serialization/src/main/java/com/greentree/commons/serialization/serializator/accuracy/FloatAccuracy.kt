package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.serialization.context.SerializationContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@AnnotationInherited
@MustBeDocumented
annotation class FloatAccuracy(
	val min: Float,
	val max: Float,
	val accuracy: Float = 0.001f,
) {

	data class Property(
		override val value: FloatAccuracy,
	) : SerializationContext.Property<FloatAccuracy> {

		override val key
			get() = Key

		companion object Key : SerializationContext.Key<Property>
	}

	data class Calculator(
		val min: Float,
		val max: Float,
		val accuracy: Float,
	) : AccuracyCalculator<Float> {

		override val countValues
			get() = ((max - min + accuracy / 2) / accuracy).toLong()

		override fun encodeValue(value: Float) = ((value - min + accuracy / 2) / accuracy).toLong()
		override fun decodeValue(value: Long) = value * accuracy + min
	}

}

val FloatAccuracy.calculator
	get() = FloatAccuracy.Calculator(min, max, accuracy)