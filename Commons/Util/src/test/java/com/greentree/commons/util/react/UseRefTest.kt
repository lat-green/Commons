package com.greentree.commons.util.react

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UseRefTest {

	fun react(context: ReactContext) = context.run {
		var ref1 by useRef(0)
		ref1++
		ref1
	}

	@Test
	fun runReact() {
		FlagReactContextProvider().use { provider ->
			assertEquals(provider.runReact(::react), 1)
			assertEquals(provider.runReact(::react), 2)
			assertEquals(provider.runReact(::react), 3)
		}
	}

	@Test
	fun runReactIfRequire() {
		FlagReactContextProvider().use { provider ->
			assertEquals(provider.runReactIfRequire(::react), 1)
			assertEquals(provider.runReactIfRequire(::react), null)
			assertEquals(provider.runReactIfRequire(::react), null)
		}
	}
}