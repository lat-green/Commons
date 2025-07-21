package com.greentree.commons.react

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UseEffectTest {

	private fun ReactContext.refreshOnEvent(action: Action) = useEffectClose(action) {
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
	fun runReact() {
		val action1 = Action()
		val action2 = Action()
		FlagReactContextProvider().use { provider ->
			assertTrue(provider.requireRefresh)
			provider.runReact {
				refreshOnEvent(action1)
			}
			action1.event()
			assertTrue(provider.requireRefresh)
			provider.runReact {
				refreshOnEvent(action2)
			}
			action1.event()
			assertFalse(provider.requireRefresh)
		}
	}

	@Test
	fun runReactIfRequire() {
		val action1 = Action()
		val action2 = Action()
		FlagReactContextProvider().use { provider ->
			assertTrue(provider.requireRefresh)
			provider.runReactIfRequire {
				refreshOnEvent(action1)
			}
			action1.event()
			assertTrue(provider.requireRefresh)
			provider.runReactIfRequire {
				refreshOnEvent(action2)
			}
			action1.event()
			assertFalse(provider.requireRefresh)
		}
	}

	@Test
	fun useEffectCloseWithDependency() {
		var callCount = 0
		FlagReactContextProvider().use { provider ->
			repeat(3) {
				provider.runReact {
					useEffectClose(Unit) {
						AutoCloseable { callCount++ }
					}
				}
			}
		}
		assertEquals(callCount, 1)
	}

	@Test
	fun useEffectClose() {
		var callCount = 0
		FlagReactContextProvider().use { provider ->
			repeat(3) {
				provider.runReact {
					useEffectClose {
						AutoCloseable { callCount++ }
					}
				}
			}
		}
		assertEquals(callCount, 3)
	}
}