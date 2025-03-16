package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.serialization.context.SerializationContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@AnnotationInherited
@MustBeDocumented
annotation class IntAccuracy(
	val min: Int,
	val max: Int,
	val accuracy: Int = 1,
) {

	data class Property(
		override val value: IntAccuracy,
	) : SerializationContext.Property<IntAccuracy> {

		override val key
			get() = Key

		companion object Key : SerializationContext.Key<Property>
	}

	data class Calculator(
		val min: Int,
		val max: Int,
		val accuracy: Int,
	) : AccuracyCalculator<Int> {

		override val countValues
			get() = ((max - min) / accuracy).toLong()

		override fun encodeValue(value: Int) = ((value - min) / accuracy).toLong()

		override fun decodeValue(value: Long) = (value * accuracy + min).toInt()
	}

}

val IntAccuracy.calculator
	get() = IntAccuracy.Calculator(min, max, accuracy)