package com.greentree.commons.react

/**
 * Интерфейс запуска React-компонента.
 * Предоставляет методы для обновления и запуска компонента.
 *
 * @param R тип возвращаемого значения
 * @see ReactContext
 * @see RequireReactRunner
 */
interface ReactRunner<R> {

	/**
	 * Вызывает обновление (перерисовку) компонента.
	 */
	fun refresh()

	/**
	 * Запускает React-компонент и возвращает результат.
	 * @return результат выполнения компонента
	 */
	fun runReact(): R
}

/**
 * Создает RequireReactRunner для провайдера контекста.
 * @param block функция компонента
 * @return RequireReactRunner
 */
fun <R> ReactContextProvider.runner(block: ReactContext.() -> R) = RequireReactRunner(this, block)