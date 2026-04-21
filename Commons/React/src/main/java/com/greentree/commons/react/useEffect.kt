package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Хук эффекта - выполняет block при изменении dependency.
 * Аналог React useEffect с проверкой по ссылке.
 *
 * @param dependency зависимость для отслеживания
 * @param block функция, выполняемая при изменении
 */
@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffect(dependency: Any, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(useDependency(dependency)) {
		block()
	}
}

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectByHash(dependency: Any, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(useDependencyByHash(dependency)) {
		block()
	}
}

@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectBySame(dependency: Any, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(useDependencyByHash(dependency)) {
		block()
	}
}

@OptIn(ExperimentalContracts::class)
@Deprecated("", ReplaceWith("block()"))
inline fun ReactContext.useEffect(block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	block()
}

/**
 * Хук эффекта с автоматическим закрытием (AutoCloseable).
 * Предыдущий ресурс закрывается при новом вызове.
 * @param dependency зависимость
 * @param block функция, возвращающая закрываемый ресурс
 */
@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectClose(dependency: Any, block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	var closeablePrevious by useRefClose<AutoCloseable> {
		it.close()
	}
	if(useDependency(dependency)) {
		closeablePrevious = block()
	}
}

/**
 * Хук эффекта с закрытием по хэшу.
 * @param dependency зависимость
 * @param block функция
 */
@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectCloseByHash(dependency: Any, block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	var closeablePrevious by useRefClose<AutoCloseable> {
		it.close()
	}
	if(useDependencyByHash(dependency)) {
		closeablePrevious = block()
	}
}

/**
 * Хук эффекта с закрытием по same.
 * @param dependency зависимость
 * @param block функция
 */
@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectCloseBySame(dependency: Any, block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	var closeablePrevious by useRefClose<AutoCloseable> {
		it.close()
	}
	if(useDependencyBySame(dependency)) {
		closeablePrevious = block()
	}
}

/**
 * Хук эффекта с однократным закрытием (только при первом рендере).
 * @param block функция
 */
@OptIn(ExperimentalContracts::class)
inline fun ReactContext.useEffectClose(block: () -> AutoCloseable) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	var previousCloseable by useRefClose<AutoCloseable> {
		it.close()
	}
	previousCloseable = block()
}

