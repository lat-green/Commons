package com.greentree.commons.util.iterator

import java.util.*

class ToListIterator<out T>(
	val origin: Iterator<T>
) : ListIterator<T>, Iterator<T> by origin {

	private val index
		get() = previous.size
	private val previous = Stack<T>()
	private val next = Stack<T>()

	override fun hasNext(): Boolean {
		return origin.hasNext() || next.isNotEmpty()
	}

	override fun next(): T {
		val next = if(next.isNotEmpty())
			next.pop()
		else
			origin.next()
		previous.add(next)
		return next
	}

	override fun hasPrevious(): Boolean {
		return previous.isNotEmpty()
	}

	override fun nextIndex() = index

	override fun previous(): T {
		val previous = previous.pop()
		next.add(previous)
		return previous
	}

	override fun previousIndex() = index - 1
}

fun <T> Iterator<T>.toListIterator() = ToListIterator(this)

