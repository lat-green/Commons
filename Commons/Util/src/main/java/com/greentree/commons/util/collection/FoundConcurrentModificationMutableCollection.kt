package com.greentree.commons.util.collection

import java.lang.ref.WeakReference

data class FoundConcurrentModificationMutableCollection<E>(
	private val origin: MutableCollection<E>,
) : MutableCollection<E> by origin {

	private val iterators = mutableListOf<WeakReference<FoundConcurrentModificationMutableIterator<E>>>()

	private fun exception(exception: ConcurrentModificationException) {
		iterators.removeIf {
			val iterator = it.get()
			iterator?.exceptions?.add(exception)
			iterator == null
		}
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
		val iterator = FoundConcurrentModificationMutableIterator(origin.iterator())
		iterators.add(WeakReference(iterator))
		return iterator
	}

	private class FoundConcurrentModificationMutableIterator<E>(
		val origin: MutableIterator<E>,
	) : MutableIterator<E> {

		val exceptions = mutableListOf<Exception>()

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
	}
}