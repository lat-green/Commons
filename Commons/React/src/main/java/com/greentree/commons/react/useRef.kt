package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun <T> ReactContext.useRef() = useRef<T?>(null)

fun <T> ReactContext.useRefClose(onClose: (T & Any) -> Unit) = useRef<T?>(null, onClose)

@OptIn(ExperimentalContracts::class)
inline fun <R> ReactContext.useFirstRef(initialValue: () -> R): Ref<R> {
	contract {
		callsInPlace(initialValue, InvocationKind.AT_MOST_ONCE)
	}
	var valueRef by useRef<R>()
	if(useFirst()) {
		valueRef = initialValue()
	}
	return valueRef as Ref<R>
}

@OptIn(ExperimentalContracts::class)
inline fun <R> ReactContext.useFirstRef(initialValue: () -> R, noinline onClose: (R & Any) -> Unit): Ref<R> {
	contract {
		callsInPlace(initialValue, InvocationKind.AT_MOST_ONCE)
	}
	var valueRef by useRefClose<R>(onClose)
	if(useFirst()) {
		valueRef = initialValue()
	}
	return valueRef as Ref<R>
}
