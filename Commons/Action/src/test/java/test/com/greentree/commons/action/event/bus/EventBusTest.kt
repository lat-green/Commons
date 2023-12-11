package test.com.greentree.commons.action.event.bus

import com.greentree.commons.action.event.bus.EventBus
import com.greentree.commons.action.event.bus.addListener
import com.greentree.commons.action.event.bus.event
import com.greentree.commons.tests.ExecuteCounter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EventBusTest {

	@Test
	fun newSimpleEventBus() {
		ExecuteCounter(1).use { counter ->
			val bus = EventBus.newSimpleEventBus<String>()
			bus.addListener("A") {
				counter.run()
			}
			bus.addListener("B") {
				fail()
			}
			bus.event("A")
		}
	}
	@Test
	fun newClassEventBus() {
		ExecuteCounter(1).use { counter ->
			val bus = EventBus.newClassEventBus()
			bus.addListener<String> {
				counter.run()
			}
			bus.addListener<Int> {
				fail()
			}
			bus.event("A")
		}
	}
}