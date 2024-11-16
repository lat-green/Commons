package com.greentree.commons.util.react

data class EventReactContextProvider(
	val onRefresh: () -> Unit,
) : ReactContextProvider {

	private val refs = mutableListOf<DataRef<*>>()
	private var next: ReactContext = HeadReactContext(refs, onRefresh)

	override fun next(): ReactContext {
		val result = next
		next = TailReactContext(refs, onRefresh)
		return result
	}

	override fun close() {
		for(ref in refs) {
			ref.close()
		}
	}
}