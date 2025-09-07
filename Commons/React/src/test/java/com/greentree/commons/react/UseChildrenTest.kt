package com.greentree.commons.react

import com.greentree.commons.tests.ExecuteCounter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UseChildrenTest {

	@Test
	fun closeInChildOnce() {
		ExecuteCounter(1).use { runCounter ->
			ExecuteCounter(1).use { closeCounter ->
				FlagReactContextProvider().use { provider ->
					val runner = SimpleReactRunner(provider) {
						val children = useChildren<Unit>()
						children.useChild(Unit).run {
							useMemoClose {
								runCounter.run()
								AutoCloseable {
									closeCounter.run()
								}
							}
						}
					}
					runner.runReact()
				}
			}
		}
	}

	@Test
	fun closeInChildTwice() {
		ExecuteCounter(1).use { runCounter ->
			ExecuteCounter(1).use { closeCounter ->
				FlagReactContextProvider().use { provider ->
					val runner = SimpleReactRunner(provider) {
						val children = useChildren<Unit>()
						children.useChild(Unit).run {
							useMemoClose {
								runCounter.run()
								AutoCloseable {
									closeCounter.run()
								}
							}
						}
					}
					runner.runReact()
					runner.runReact()
				}
			}
		}
	}

	@Test
	fun closeInChildTwice6() {
		ExecuteCounter(5).use { runCounter ->
			ExecuteCounter(4).use { memoCounter ->
				ExecuteCounter(4).use { closeCounter ->
					FlagReactContextProvider().use { provider ->
						val list = listOf(listOf("A", "B"), listOf("B"), listOf("A", "C")).iterator()
						val runner = SimpleReactRunner(provider) {
							useForEach(list.next()) {
								runCounter.run()
								useMemoClose {
									memoCounter.run()
									AutoCloseable {
										closeCounter.run()
									}
								}
							}
						}
						while(list.hasNext()) {
							runner.runReact()
						}
					}
				}
			}
		}
	}

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
