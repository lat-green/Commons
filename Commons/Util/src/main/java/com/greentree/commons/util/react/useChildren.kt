package com.greentree.commons.util.react

interface ChildrenReactContext {

	fun useChild(id: Any): ReactContext
}

fun ReactContext.useChildren(): ChildrenReactContext = useMemoClose(Unit) {
	val parent = this
	val children = mutableMapOf<Any, ReactContextProvider>()
	object : ChildrenReactContext, AutoCloseable {
		override fun useChild(id: Any): ReactContext {
			return children.getOrPut(id) {
				EventReactContextProvider {
					parent.refresh()
				}
			}.next()
		}

		override fun close() {
			children.values.forEach {
				it.close()
			}
			children.clear()
		}
	}
}

inline fun <T : Any> Sequence<T>.forEachReact(context: ReactContext, block: ReactContext.(T) -> Unit) {
	val children = context.useChildren()
	for(t in this)
		children.useChild(t).block(t)
}

inline fun <T : Any> Iterable<T>.forEachReact(context: ReactContext, block: ReactContext.(T) -> Unit) {
	val children = context.useChildren()
	for(t in this)
		children.useChild(t).block(t)
}

enum class IfBlock { THEN, ELSE }

inline fun <R> Boolean.ifReact(context: ReactContext, then: ReactContext.() -> R, `else`: ReactContext.() -> R): R {
	val children = context.useChildren()
	return if(this)
		children.useChild(IfBlock.THEN).run(then)
	else
		children.useChild(IfBlock.ELSE).run(`else`)
}