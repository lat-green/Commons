package com.greentree.commons.util.react

open class FlagReactContextProvider : ReactContextProvider {

	private val refs = mutableListOf<DataRef<*>>()
	private var next: ReactContext = HeadReactContext()
	override var requireRefresh: Boolean = true
		protected set

	override fun refresh() {
		requireRefresh = true
	}

	override fun next(): ReactContext {
		requireRefresh = false
		val result = next
		next = TailReactContext()
		return result
	}

	override fun close() {
		for(ref in refs) {
			ref.close()
		}
	}

	private inner class HeadReactContext : ReactContext {

		override fun refresh() = this@FlagReactContextProvider.refresh()

		override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
			val ref = DataRef(initialValue, onClose)
			refs.add(ref)
			return ref
		}
	}

	private inner class TailReactContext : ReactContext {

		private var refIndex = 0

		override fun refresh() = this@FlagReactContextProvider.refresh()

		override fun <T> useRef(initialValue: T, onClose: (T) -> Unit): Ref<T> {
			return refs[refIndex++] as Ref<T>
		}
	}
}

