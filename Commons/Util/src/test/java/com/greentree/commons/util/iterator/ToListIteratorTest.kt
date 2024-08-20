package com.greentree.commons.util.iterator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class ToListIteratorTest {

	data object ListArgumentsProvider : ArgumentsProvider {

		override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
			return Stream.of(
				Arguments.of(listOf<Any>()),
				Arguments.of(listOf(Unit)),
				Arguments.of(listOf(1, 2, 3)),
				Arguments.of(listOf("A", "B", "C")),
				Arguments.of((0 .. 1_000).toList()),
			)
		}
	}

	@ParameterizedTest
	@ArgumentsSource(ListArgumentsProvider::class)
	fun baseIterating(list: List<*>) {
		val iterator1 = list.listIterator()
		val iterator2 = list.iterator().toListIterator()
		while(iterator1.hasNext() && iterator2.hasNext()) {
			val v1 = iterator1.next()
			val v2 = iterator2.next()
			assertEquals(v1, v2)
		}
		assertEquals(iterator1.hasNext(), iterator2.hasNext())
	}

	@ParameterizedTest
	@ArgumentsSource(ListArgumentsProvider::class)
	fun previousIterating(list: List<*>) {
		val iterator1 = list.listIterator()
		val iterator2 = list.iterator().toListIterator()
		while(iterator1.hasNext() && iterator2.hasNext()) {
			assertEquals(iterator1.nextIndex(), iterator2.nextIndex())
			assertEquals(iterator1.previousIndex(), iterator2.previousIndex())
			iterator1.next()
			iterator2.next()
		}
		assertEquals(iterator1.hasPrevious(), iterator2.hasPrevious())
		while(iterator1.hasPrevious() && iterator2.hasPrevious()) {
			assertEquals(iterator1.nextIndex(), iterator2.nextIndex())
			assertEquals(iterator1.previousIndex(), iterator2.previousIndex())
			val v1 = iterator1.previous()
			val v2 = iterator2.previous()
			assertEquals(v1, v2)
		}
		assertEquals(iterator1.hasPrevious(), iterator2.hasPrevious())
	}

	@ParameterizedTest
	@ArgumentsSource(ListArgumentsProvider::class)
	fun previousAndBaseIterating(list: List<*>) {
		val iterator1 = list.listIterator()
		val iterator2 = list.iterator().toListIterator()
		while(iterator1.hasNext() && iterator2.hasNext()) {
			iterator1.next()
			iterator2.next()
		}
		while(iterator1.hasPrevious() && iterator2.hasPrevious()) {
			iterator1.previous()
			iterator2.previous()
		}
		while(iterator1.hasNext() && iterator2.hasNext()) {
			assertEquals(iterator1.nextIndex(), iterator2.nextIndex())
			assertEquals(iterator1.previousIndex(), iterator2.previousIndex())
			val v1 = iterator1.next()
			val v2 = iterator2.next()
			assertEquals(v1, v2)
		}
	}
}