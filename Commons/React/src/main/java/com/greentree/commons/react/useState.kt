package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Класс состояния React-компонента.
 * При изменении значения автоматически вызывает перерисовку компонента.
 *
 * @param T тип значения состояния
 * @param context React-контекст
 * @param ref ссылка на значение
 * @see useState
 */
data class State<T>(
	val context: ReactContext,
	val ref: Ref<T>,
) : Ref<T> {

	override var value: T
		get() = ref.value
		set(value) {
			ref.value = value
			context.refresh()
		}
}

/**
 * Хук useState без начального значения (null).
 * @param T тип значения
 * @return State с null начальным значением
 */
fun <T> ReactContext.useState() = useState<T?>(null)

/**
 * Хук useState с начальным значением.
 * @param T тип значения
 * @param initial начальное значение
 * @return State с указанным начальным значением
 */
fun <T> ReactContext.useState(initial: T) = State(this, useRef(initial))

/**
 * Хук useState с ленивым начальным значением (вычисляется только при первом рендере).
 * @param T тип значения
 * @param initial функция, возвращающая начальное значение
 * @return State
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> ReactContext.useState(initial: () -> T): State<T> {
	contract {
		callsInPlace(initial, InvocationKind.AT_MOST_ONCE)
	}
	return State(this, useFirstRef(initial))
}

