package com.greentree.commons.util.react

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
fun <T> ReactContext.useState(initial: () -> T) = State(this, useFirstRef(initial))

