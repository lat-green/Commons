package com.greentree.commons.util.react

class TempReactContextProvider : ReactContextProvider {

	private val refs = mutableListOf<Ref<*>>()

	override fun next(): ReactContext = HeadReactContext()

	override val requireRefresh: Boolean
		get() = true

	override fun refresh() {
	}

	override fun close() {
		for(ref in refs) {
			if(ref is AutoCloseable) {
				ref.close()
			}
		}
	}

	private inner class HeadReactContext : ReactContext {

		override fun refresh() {
		}

		override fun useFirst(): Boolean = false

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
}

