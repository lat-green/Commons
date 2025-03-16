package com.greentree.commons.util.react

data class State<T>(
	val context: ReactContext,
	val ref: Ref<T>,
) : Ref<T> {

	private val refValue = ref.value
	override var value: T
		get() = refValue
		set(value) {
			ref.value = value
			context.refresh()
		}
}

fun <T> ReactContext.useState() = useState<T?>(null)

fun <T> ReactContext.useState(initial: T) = State(this, useRef(initial))
