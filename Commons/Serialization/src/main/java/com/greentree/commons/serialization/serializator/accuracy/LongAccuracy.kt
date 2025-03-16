package com.greentree.commons.serialization.serializator.accuracy

import com.greentree.commons.annotation.AnnotationInherited
import com.greentree.commons.serialization.context.SerializationContext

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@AnnotationInherited
@MustBeDocumented
annotation class LongAccuracy(
	val min: Long,
	val max: Long,
	val accuracy: Long = 1,
) {

	data class Property(
		override val value: LongAccuracy,
	) : SerializationContext.Property<LongAccuracy> {

		override val key
			get() = Key

		companion object Key : SerializationContext.Key<Property>
	}

	data class Calculator(
		val min: Long,
		val max: Long,
		val accuracy: Long,
	) : AccuracyCalculator<Long> {

		override val countValues
			get() = (max - min) / accuracy

		override fun encodeValue(value: Long) = (value - min) / accuracy

		override fun decodeValue(value: Long) = value * accuracy + min
	}

}

val LongAccuracy.calculator
	get() = LongAccuracy.Calculator(min, max, accuracy)