package com.greentree.commons.util

import com.greentree.commons.util.react.FlagReactContextProvider
import com.greentree.commons.util.react.ReactContext
import com.greentree.commons.util.react.runReact
import com.greentree.commons.util.react.useEffectClose
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UseEffectTest {

	fun ReactContext.foo(action: Action) = useEffectClose(action) {
		action.addListener {
			refresh()
		}
	}

	class Action {

		private val listeners = mutableListOf<() -> Unit>()

		fun event() {
			for(listener in listeners) {
				listener.invoke()
			}
		}

		fun addListener(listener: () -> Unit): AutoCloseable {
			listeners.add(listener)
			return AutoCloseable {
				listeners.remove(listener)
			}
		}
	}

	@Test
	fun test1() {
		val action1 = Action()
		val action2 = Action()
		FlagReactContextProvider().use { provider ->
			provider.runReact {
				foo(action1)
			}
			action1.event()
			assertTrue(provider.requireRefresh)
			provider.runReact {
				foo(action2)
			}
			action1.event()
			assertFalse(provider.requireRefresh)
		}
	}
}