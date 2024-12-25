package test.com.greentree.commons.action.event.bus

import com.greentree.commons.action.event.bus.EventBusImpl
import com.greentree.commons.action.event.bus.addListener
import com.greentree.commons.tests.ExecuteCounter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EventBusTest {

	@Test
	fun newSimpleEventBus() {
		ExecuteCounter(1).use { counter ->
			val bus = EventBusImpl<String>()
			bus.topic { it }.addListener("A") {
				counter.run()
			}
			bus.topic { it }.addListener("B") {
				fail()
			}
			bus.event("A")
		}
	}

	@Test
	fun newClassEventBus() {
		ExecuteCounter(1).use { counter ->
			val bus = EventBusImpl<Any>()
			val topic = bus.topic { it::class }
			topic.addListener { _: String ->
				counter.run()
			}
			topic.addListener { _: Int ->
				fail()
			}
			bus.event("A")
		}
	}
}