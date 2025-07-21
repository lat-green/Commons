package com.greentree.commons.react

interface ReactContext {

	fun refresh()

	fun useFirst(): Boolean

	fun <T> useRef(initialValue: T, onClose: (T & Any) -> Unit): Ref<T>

	fun <T> useRef(initialValue: T): Ref<T>
}
