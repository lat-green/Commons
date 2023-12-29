package test.com.greentree.commons.serialization.test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class JsonTest {

	data class Person(val name: Name, val age: Int)

	interface Name {

		val value: String
	}

	data class NameImpl(override val value: String) : Name

	@Test
	fun test1() {
		val person = Person(NameImpl("ara"), 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<Person>(json)
		Assertions.assertEquals(person, person2)
	}
}