package com.greentree.commons.tests

import com.greentree.commons.util.collection.FoundConcurrentModificationMutableCollection
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FoundConcurrentModificationMutableCollectionTest {

	@Test
	fun add() {
		val list = FoundConcurrentModificationMutableCollection(mutableListOf("A", "B", "C"))
		val iterator = list.iterator()
		assertTrue(iterator.hasNext())
		assertEquals(iterator.next(), "A")
		list.add("D")
		val e = assertThrows(ConcurrentModificationException::class.java) {
			iterator.next()
		}
		assertEquals(e.suppressed.size, 1)
		assertEquals(e.suppressed[0].message, "add D")
		assertInstanceOf(ConcurrentModificationException::class.java, e.suppressed[0])
	}
}