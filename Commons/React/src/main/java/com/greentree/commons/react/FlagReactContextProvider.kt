package com.greentree.commons.react

open class FlagReactContextProvider : ReactContextProvider {

	private val refs = mutableListOf<Ref<*>>()
	private var next: ReactContext = HeadReactContext()
	override var requireRefresh: Boolean = true
		protected set

	override fun refresh() {
		requireRefresh = true
	}

	override fun next(): ReactContext {
		requireRefresh = false
		val result = next
		next = TailReactContext()
		return result
	}

	override fun close() {
		for(ref in refs) {
			if(ref is AutoCloseable) {
				ref.close()
			}
		}
	}

	private inner class HeadReactContext : ReactContext {

		override fun refresh() = this@FlagReactContextProvider.refresh()

		override fun useFirst(): Boolean = true

		override fun <T> useRef(initialValue: T, onClose: (T & Any) -> Unit): Ref<T> {
			val ref = CloseableRef(initialValue, onClose)
			refs.add(ref)
			return ref
		}

		override fun <T> useRef(initialValue: T): Ref<T> {
			val ref = DataRef(initialValue)
			refs.add(ref)
			return ref
		}
	}

	private inner class TailReactContext : ReactContext {

		private var refIndex = 0

		override fun useFirst(): Boolean = false

		override fun refresh() = this@FlagReactContextProvider.refresh()

		override fun <T> useRef(initialValue: T, onClose: (T & Any) -> Unit): Ref<T> {
			return refs[refIndex++] as Ref<T>
		}

		override fun <T> useRef(initialValue: T): Ref<T> {
			return refs[refIndex++] as Ref<T>
		}
	}
}

