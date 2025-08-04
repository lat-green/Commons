package com.greentree.commons.react

import kotlin.test.Test
import kotlin.test.assertEquals

class ReactRunnerKtTest {

	@Test
	fun simpleReactRunner() {
		val results = listOf("A", "B", "C")
		val runner = SimpleReactRunner {
			var index by useRef(-1)
			index++
			results[index]
		}
		assertEquals(runner.runReact(), results[0])
		assertEquals(runner.runReact(), results[1])
		assertEquals(runner.runReact(), results[2])
	}

	@Test
	fun requireReactRunner() {
		val results = listOf("A", "B", "C")
		val runner = RequireReactRunner {
			var index by useRef(-1)
			index++
			results[index]
		}
		assertEquals(runner.runReact(), results[0])
		assertEquals(runner.runReact(), results[0])
		runner.refresh()
		assertEquals(runner.runReact(), results[1])
	}
}