package com.greentree.commons.util.react

class FlagReactContextProvider : ReactContextProvider, () -> Unit {

	private val refs = mutableListOf<DataRef<*>>()
	private var next: ReactContext = HeadReactContext(refs, this)
	var requireRefresh: Boolean = false
		private set

	override fun next(): ReactContext {
		requireRefresh = false
		val result = next
		next = TailReactContext(refs, this)
		return result
	}

	override fun close() {
		for(ref in refs) {
			ref.close()
		}
	}

	override fun invoke() {
		requireRefresh = true
	}
}