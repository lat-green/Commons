package com.greentree.commons.util.collection

data class FoundConcurrentModificationMutableCollection<E>(
	private val origin: MutableCollection<E>
) : MutableCollection<E> by origin {

	private val exceptions = mutableListOf<Exception>()

	private fun exception(exception: ConcurrentModificationException) {
		exceptions.add(exception)
	}

	override fun add(element: E): Boolean {
		exception(ConcurrentModificationException("add $element"))
		return origin.add(element)
	}

	override fun addAll(elements: Collection<E>): Boolean {
		exception(ConcurrentModificationException("addAll $elements"))
		return origin.addAll(elements)
	}

	override fun remove(element: E): Boolean {
		exception(ConcurrentModificationException("remove $element"))
		return origin.remove(element)
	}

	override fun removeAll(elements: Collection<E>): Boolean {
		exception(ConcurrentModificationException("removeAll $elements"))
		return origin.removeAll(elements)
	}

	override fun retainAll(elements: Collection<E>): Boolean {
		exception(ConcurrentModificationException("retainAll $elements"))
		return origin.retainAll(elements)
	}

	override fun clear() {
		exception(ConcurrentModificationException("clear"))
		return origin.clear()
	}

	override fun iterator(): MutableIterator<E> {
		return FoundConcurrentModificationMutableIterator(origin.iterator())
	}

	private inline fun <R> checkConcurrentModification(block: () -> R): R {
		try {
			return block()
		} catch(e: ConcurrentModificationException) {
			for(exception in exceptions) {
				e.addSuppressed(exception)
			}
			exceptions.clear()
			throw e
		}
	}

	private inner class FoundConcurrentModificationMutableIterator<E>(
		val origin: MutableIterator<E>
	) : MutableIterator<E> {

		override fun hasNext(): Boolean {
			return checkConcurrentModification {
				origin.hasNext()
			}
		}

		override fun next(): E {
			return checkConcurrentModification {
				origin.next()
			}
		}

		override fun remove() {
			exceptions.add(ConcurrentModificationException("remove"))
			checkConcurrentModification {
				origin.remove()
			}
		}
	}
}