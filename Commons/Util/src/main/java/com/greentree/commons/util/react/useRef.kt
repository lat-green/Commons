package com.greentree.commons.util.react

fun <T> ReactContext.useRef() = useRef<T?>(null)

fun <T> ReactContext.useRefClose(onClose: (T & Any) -> Unit) = useRef<T?>(null, onClose)

inline fun <R> ReactContext.useFirstRef(initialValue: () -> R): Ref<R> {
	var valueRef by useRef<R>()
	if(useFirst()) {
		valueRef = initialValue()
	}
	return valueRef as Ref<R>
}

inline fun <R> ReactContext.useFirstRef(initialValue: () -> R, noinline onClose: (R & Any) -> Unit): Ref<R> {
	var valueRef by useRefClose<R>(onClose)
	if(useFirst()) {
		valueRef = initialValue()
	}
	return valueRef as Ref<R>
}
