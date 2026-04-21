package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Интерфейс провайдера React-контекста.
 * Предоставляет методы для управления жизненным циклом компонента.
 *
 * @see ReactContext
 * @see FlagReactContextProvider
 * @see EventReactContextProvider
 */
interface ReactContextProvider : AutoCloseable {

	/**
	 * Требуется ли обновление компонента.
	 * @return true, если нужен повторный рендер
	 */
	val requireRefresh: Boolean

	/**
	 * Принудительно обновляет компонент.
	 */
	fun refresh()

	/**
	 * Возвращает следующий контекст для рендера.
	 * @return ReactContext
	 */
	fun next(): ReactContext
}

/**
 * Запускает React-компонент и перерисовывает при необходимости.
 * @param block функция компонента
 * @return результат выполнения
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> ReactContextProvider.runReact(block: ReactContext.() -> R): R {
	contract {
		callsInPlace(block, InvocationKind.AT_LEAST_ONCE)
	}
	var result: R
	do {
		result = next().run(block)
	} while(requireRefresh)
	return result
}

/**
 * Запускает React-компонент только если требуется обновление.
 * @param block функция компонента
 * @return результат или null
 */
inline fun <R> ReactContextProvider.runReactIfRequire(block: ReactContext.() -> R): R? {
	var result: R
	if(!requireRefresh)
		return null
	do {
		result = next().run(block)
	} while(requireRefresh)
	return result
}

