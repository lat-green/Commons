package com.greentree.commons.util.react

class CloseableRef<T>(
	value: T,
	val onClose: (T & Any) -> Unit,
) : Ref<T>, AutoCloseable {

	override var value: T = value
		set(value) {
			field?.let {
				onClose(it)
			}
			field = value
		}

	override fun close() {
		value?.let {
			onClose(it)
		}
	}
}

