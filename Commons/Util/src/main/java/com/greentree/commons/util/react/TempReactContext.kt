package com.greentree.commons.util.react

class TempReactContext : ReactContext, AutoCloseable {

	private val refs = mutableListOf<DataRef<*>>()

	override fun refresh() {
	}

	override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
		val ref = DataRef(initialValue, onClose)
		refs.add(ref)
		return ref
	}

	override fun close() {
		for(ref in refs) {
			ref.close()
		}
	}
}