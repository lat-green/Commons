package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Хук useRef без начального значения (null).
 * @param T тип значения
 * @return Ref с null
 */
fun <T> ReactContext.useRef() = useRef<T?>(null)

/**
 * Хук useRef с колбэком закрытия.
 * @param T тип значения
 * @param onClose колбэк, вызываемый при закрытии
 * @return Ref
 */
fun <T> ReactContext.useRefClose(onClose: (T & Any) -> Unit) = useRef<T?>(null, onClose)

/**
 * Хук useFirstRef с ленивым начальным значением (только при первом рендере).
 * @param R тип значения
 * @param initialValue функция, возвращающая начальное значение
 * @return Ref
 */
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

/**
 * Хук useFirstRef с колбэком закрытия.
 * @param R тип значения
 * @param initialValue функция, возвращающая начальное значение
 * @param onClose колбэк, вызываемый при закрытии
 * @return Ref
 */
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
