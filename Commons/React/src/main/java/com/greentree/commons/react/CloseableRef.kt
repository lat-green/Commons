package com.greentree.commons.react

class CloseableRef<T>(
	value: T,
	val onClose: (T & Any) -> Unit,
) : Ref<T>, AutoCloseable {

	override var value: T = value
		set(value) {
			if(field != value) {
				field?.let {
					onClose(it)
				}
				field = value
			}
		}

	override fun toString(): String {
		return "CloseableRef($value)"
	}

	override fun close() {
		value?.let {
			onClose(it)
		}
	}
}

