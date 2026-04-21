package com.greentree.commons.react

import kotlin.reflect.KProperty

/**
 * Интерфейс ссылки (ref) для хранения изменяемого значения.
 * Используется для сохранения состояния между рендерами без вызова перерисовки.
 *
 * @param T тип хранимого значения
 * @see ReactContext
 * @see useRef
 */
interface Ref<T> {

	/**
	 * Текущее значение ссылки.
	 */
	var value: T
}

/**
 * Оператор для получения значения ref (делегат свойства).
 * @param thisRef владелец свойства
 * @param property свойство
 * @return текущее значение
 */
operator fun <T> Ref<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

/**
 * Оператор для установки значения ref (делегат свойства).
 * @param thisRef владелец свойства
 * @param property свойство
 * @param value новое значение
 */
operator fun <T> Ref<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
	this.value = value
}
