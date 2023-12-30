package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import test.data.Person

class JsonTest {

	@Test
	fun test1() {
		val person = Person("ara", 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<Person>(json)
		Assertions.assertEquals(person, person2)
	}
}