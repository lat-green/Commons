package com.greentree.commons.react

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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

fun <T> ReactContext.useState() = useState<T?>(null)

fun <T> ReactContext.useState(initial: T) = State(this, useRef(initial))

@OptIn(ExperimentalContracts::class)
inline fun <T> ReactContext.useState(initial: () -> T): State<T> {
	contract {
		callsInPlace(initial, InvocationKind.AT_MOST_ONCE)
	}
	return State(this, useFirstRef(initial))
}

