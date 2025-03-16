package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.format.Bytes
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ArgumentUtils.*
import java.io.ByteArrayOutputStream
import kotlin.reflect.KClass

class ClassSerializeUtilTest {

	private fun serialize(cls: Class<*>) = ByteArrayOutputStream().use {
		Bytes.encoder(it).encodeClass(cls)
		it.toByteArray()
	}

	private fun check(cls: Class<*>, size: Int = 3) {
		val bytes = serialize(cls)
		assertTrue(bytes.size == size) {
			println("$cls ${bytes.size}")
			"$cls ${bytes.contentToString()} ${String(bytes)} (${bytes.size} != $size)"
		}
	}

	private fun check(cls: KClass<*>, size: Int) = check(cls.java, size)
	private fun check(cls: KClass<*>) = check(cls.java)

	@Test
	fun String() {
		check(String::class)
	}

	@Test
	fun Int() {
		check(Int::class)
	}

	@Test
	@DisplayName("Array<Int>")
	fun ArrayInt() {
		check(Array<Int>::class, 4)
	}

	@Test
	@DisplayName("Array<String>")
	fun ArrayString() {
		check(Array<String>::class, 4)
	}

	@Test
	fun IntArray() {
		check(IntArray::class, 4)
	}

	@Test
	fun Any() {
		check(Any::class)
	}

	@Test
	fun ArrayList() {
		check(ArrayList::class)
	}

	@Test
	fun StringBuilder() {
		check(StringBuilder::class, 15)
	}

	@Test
	fun Integer() {
		check(Integer::class)
	}
}