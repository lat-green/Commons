package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import test.data.Anton
import test.data.CustomPerson
import test.data.NameImpl
import test.data.ObjectPerson
import test.data.Person

class JsonTest {

	@Test
	fun testBase() {
		val person = Person("ara", 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<Person>(json)
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testCustom() {
		val person = CustomPerson(NameImpl("ara"), 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<CustomPerson>(json)
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testCustom_withObject() {
		val person = CustomPerson(Anton, 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<CustomPerson>(json)
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testObject() {
		val person = ObjectPerson(Anton, 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<ObjectPerson>(json)
		Assertions.assertEquals(person, person2)
	}
}