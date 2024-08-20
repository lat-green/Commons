package com.greentree.commons.util.iterator

import java.util.*

class ToListIterator<out T>(
	val origin: Iterator<T>
) : ListIterator<T>, Iterator<T> by origin {

	private val previous = Stack<T>()
	private val next: Queue<T> = LinkedList()

	override fun hasNext(): Boolean {
		return origin.hasNext() || next.isNotEmpty()
	}

	override fun next(): T {
		if(next.isNotEmpty())
			return next.remove()
		val next = origin.next()
		previous.add(next)
		return next
	}

	override fun hasPrevious(): Boolean {
		TODO("Not yet implemented")
	}

	override fun nextIndex(): Int {
		TODO("Not yet implemented")
	}

	override fun previous(): T {
		val previous = previous.pop()
		next.add(previous)
		return previous
	}

	override fun previousIndex(): Int {
		TODO("Not yet implemented")
	}
}

fun <T> Iterator<T>.toListIterator() = ToListIterator(this)

