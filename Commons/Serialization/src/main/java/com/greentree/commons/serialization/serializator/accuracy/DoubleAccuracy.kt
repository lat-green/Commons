package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.serialization.context.SerializationContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@AnnotationInherited
@MustBeDocumented
annotation class DoubleAccuracy(
	val min: Double,
	val max: Double,
	val accuracy: Double = 0.001,
) {

	data class Property(
		override val value: DoubleAccuracy,
	) : SerializationContext.Property<DoubleAccuracy> {

		override val key
			get() = Key

		companion object Key : SerializationContext.Key<Property>
	}

	data class Calculator(
		val min: Double,
		val max: Double,
		val accuracy: Double,
	) : AccuracyCalculator<Double> {

		override val countValues
			get() = ((max - min + accuracy / 2) / accuracy).toLong()

		override fun encodeValue(value: Double) = ((value - min + accuracy / 2) / accuracy).toLong()
		override fun decodeValue(value: Long) = value * accuracy + min
	}
}

val DoubleAccuracy.calculator
	get() = DoubleAccuracy.Calculator(min, max, accuracy)