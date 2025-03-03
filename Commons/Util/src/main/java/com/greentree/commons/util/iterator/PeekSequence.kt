package com.greentree.commons.util.iterator

data class PeekSequence<T>(
	val origin: Sequence<T>,
	val block: (T) -> Unit,
) : Sequence<T> {

	override fun iterator() = PeekIterator<T>(origin.iterator(), block)

	data class PeekIterator<T>(
		val origin: Iterator<T>,
		val block: (T) -> Unit
	) : Iterator<T> by origin {

		override fun next(): T {
			val result = origin.next()
			block(result)
			return result
		}
	}
}

fun <T> Sequence<T>.peek(block: (T) -> Unit) = PeekSequence(this, block)