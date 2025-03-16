package com.greentree.commons.util.react

interface ReactContext {

	fun refresh()

	fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T>
}
