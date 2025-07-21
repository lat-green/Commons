package com.greentree.commons.react

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UseChildrenTest {

	@Test
	fun doubleUseChild() {
		FlagReactContextProvider().use { provider ->
			provider.runReact {
				val children = useChildren<String>()
				var first by children.useChild("A").useRef<String>()
				var second by children.useChild("A").useRef<String>()
				first = TEXT
				assertEquals(TEXT, second)
			}
		}
	}

	@Test
	fun equalsInNextIteration() {
		FlagReactContextProvider().use { provider ->
			provider.runReact {
				val children = useChildren<String>()
				children.useChild("A").run {
					var ref by useRef<String>()
					ref = TEXT
				}
			}
			provider.runReact {
				val children = useChildren<String>()
				children.useChild("A").run {
					var ref by useRef<String>()
					assertEquals(TEXT, ref)
				}
			}
		}
	}

	@Test
	fun third() {
		FlagReactContextProvider().use { provider ->
			provider.runReact {
				val children = useChildren<String>()
				children.useChild("A").run {
					var ref by useRef<String>()
					ref = TEXT
				}
			}
			provider.runReact {
				val children = useChildren<String>()
			}
			provider.runReact {
				val children = useChildren<String>()
				children.useChild("A").run {
					var ref by useRef<String>()
					assertNotEquals(TEXT, ref)
				}
			}
		}
	}

	companion object {

		private val TEXT = "text"
	}
}
