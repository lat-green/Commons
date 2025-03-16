package com.greentree.commons.util.react

class TempReactContextProvider : ReactContextProvider {

	private val refs = mutableListOf<DataRef<*>>()

	override fun next(): ReactContext = HeadReactContext()

	override val requireRefresh: Boolean
		get() = true

	override fun refresh() {
	}

	override fun close() {
		for(ref in refs) {
			ref.close()
		}
	}

	private inner class HeadReactContext : ReactContext {

		override fun refresh() {
		}

		override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
			val ref = DataRef(initialValue, onClose)
			refs.add(ref)
			return ref
		}
	}
}

