package com.greentree.commons.util.react

fun <T> ReactContext.useState() = useState<T?>(null)

fun <T> useState() = REACT.get().useState<T>()

fun <T> ReactContext.useState(initialValue: T) = object : Ref<T> {
	var ref by useRef(initialValue)
	override var value: T
		get() = ref
		set(value) {
			ref = value
			refresh()
		}
}

fun <T> useState(initialValue: T) = REACT.get().useState(initialValue)
