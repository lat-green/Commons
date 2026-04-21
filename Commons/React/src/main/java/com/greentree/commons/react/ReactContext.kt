package com.greentree.commons.react

/**
 * Интерфейс контекста React для управления состоянием компонента.
 * Предоставляет методы для обновления компонента и работы со ссылками (refs).
 * 
 * @see ReactContextProvider
 * @see Ref
 */
interface ReactContext {

	/**
	 * Вызывает обновление (перерисовку) компонента.
	 */
	fun refresh()

	/**
	 * Возвращает true, если это первый рендер компонента.
	 * @return true при первом рендере
	 */
	fun useFirst(): Boolean

	/**
	 * Создает ссылку (ref) с начальным значением и колбэком закрытия.
	 * @param T тип значения
	 * @param initialValue начальное значение
	 * @param onClose колбэк, вызываемый при закрытии
	 * @return Ref<T>
	 */
	fun <T> useRef(initialValue: T, onClose: (T & Any) -> Unit): Ref<T>

	/**
	 * Создает ссылку (ref) с начальным значением.
	 * @param T тип значения
	 * @param initialValue начальное значение
	 * @return Ref<T>
	 */
	fun <T> useRef(initialValue: T): Ref<T>
}
