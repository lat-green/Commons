package com.greentree.commons.util.react

class DataRef<T>(
	value: T,
	val onClose: (T) -> Unit,
) : Ref<T>, AutoCloseable {

	override var value: T = value
		set(value) {
				onClose(field)
				field = value
		}

	override fun close() {
		onClose(value)
	}
}