package com.greentree.commons.util.react

class TailReactContext(
	private val refs: List<Ref<*>>,
	val onRefresh: () -> Unit,
) : ReactContext {

	private var refIndex = 0

	override fun refresh() = onRefresh()

	override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
		return refs[refIndex++] as Ref<T>
	}
}