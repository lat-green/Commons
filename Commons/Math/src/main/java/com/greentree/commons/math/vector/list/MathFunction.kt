package com.greentree.commons.math.vector.list

interface MathFunction<T, R> : (T) -> R {

	override fun invoke(arg: T): R
	fun invokeInverse(arg: R): T

	fun toInverse(): MathFunction<R, T> {
		return object : MathFunction<R, T> {
			override fun invoke(arg: R) = this@MathFunction.invokeInverse(arg)
			override fun invokeInverse(arg: T) = this@MathFunction.invoke(arg)
		}
	}
}
