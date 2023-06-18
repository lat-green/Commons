package com.greentree.commons.math.vector.list

import com.greentree.commons.math.vector.AbstractVector3f
import java.util.function.Predicate

private inline operator fun <T> Predicate<in T>.invoke(arg: T): Boolean {
	return test(arg)
}

class FunctionVector3fCollection(private val function: Predicate<in AbstractVector3f>) : AbstractVector3fCollection {

	override val size: Int
		get() = throw UnsupportedOperationException()

	override fun contains(element: AbstractVector3f): Boolean {
		return function(element)
	}

	override fun containsAll(elements: Collection<AbstractVector3f>): Boolean {
		return elements.all { contains(it) }
	}

	override fun isEmpty(): Boolean {
		throw UnsupportedOperationException()
	}

	override fun iterator(): Iterator<AbstractVector3f> {
		throw UnsupportedOperationException()
	}
}