package test.com.greentree.commons.action.react

import com.greentree.commons.action.property.MutableReactivePropertyImpl
import com.greentree.commons.action.react.ReactProperty
import com.greentree.commons.action.react.useProperty
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ReactPropertyTest {

	@Test
	fun mapFromMutable() {
		val origin = MutableReactivePropertyImpl("A")
		val property = ReactProperty {
			val a = useProperty(origin)
			a + a
		}
		assertEquals(property.value, "AA")
		origin.value = "B"
		assertEquals(property.value, "BB")
	}

	@Test
	fun mapFromMutableDoubleChange() {
		val origin = MutableReactivePropertyImpl("A")
		val property = ReactProperty {
			val a = useProperty(origin)
			a + a
		}
		assertEquals(property.value, "AA")
		origin.value = "B"
		assertEquals(property.value, "BB")
		origin.value = "C"
		assertEquals(property.value, "CC")
	}

	@Test
	fun mapFromDeepMutable() {
		val origin = MutableReactivePropertyImpl("A")
		val deepProperty = ReactProperty {
			val a = useProperty(origin)
			a + a
		}
		val property = ReactProperty {
			val a = useProperty(deepProperty)
			a
		}
		assertEquals(property.value, "AA")
		origin.value = "B"
		assertEquals(property.value, "BB")
	}

	@Test
	fun mergeFromMutable() {
		val first = MutableReactivePropertyImpl("A")
		val second = MutableReactivePropertyImpl("B")
		val property = ReactProperty {
			val a = useProperty(first)
			val b = useProperty(second)
			a + b
		}
		assertEquals(property.value, "AB")
		first.value = "C"
		assertEquals(property.value, "CB")
	}
}
