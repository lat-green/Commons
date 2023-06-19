package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector2f
import java.util.function.Predicate

private inline operator fun <T> Predicate<in T>.invoke(arg: T): Boolean {
	return test(arg)
}

class FunctionVector2fCollection(private val function: Predicate<in AbstractVector2f>) : AbstractVector2fCollection {

	override val size: Int
		get() = throw UnsupportedOperationException()

	override fun contains(element: AbstractVector2f): Boolean {
		return function(element)
	}

	override fun containsAll(elements: Collection<AbstractVector2f>): Boolean {
		return elements.all { contains(it) }
	}

	override fun isEmpty(): Boolean {
		throw UnsupportedOperationException()
	}

	override fun iterator(): Iterator<AbstractVector2f> {
		throw UnsupportedOperationException()
	}
}