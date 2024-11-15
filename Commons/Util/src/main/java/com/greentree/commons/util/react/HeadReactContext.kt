package com.greentree.commons.util.react

class HeadReactContext(
	private val refs: MutableList<DataRef<*>>,
	val onRefresh: () -> Unit,
) : ReactContext {

	override fun refresh() = onRefresh()

	override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
		val ref = DataRef(initialValue, onClose)
		refs.add(ref)
		return ref
	}
}