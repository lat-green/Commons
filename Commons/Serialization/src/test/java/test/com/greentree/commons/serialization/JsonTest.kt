package test.com.greentree.commons.serialization

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.greentree.commons.serialization.data.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
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
		assertEquals(person, person2)
	}

	@Test
	fun testBaseDecodeTo() {
		val person = Person("ara", 12)
		val json = JsonObject().also { person ->
			person.add("name", JsonPrimitive("anton"))
			person.add("age", JsonPrimitive(13))
		}
		val person2 = Json.decodeFromStringTo(json, person)
		assertEquals(Person("anton", 13), person2)
	}

	@Test
	fun testCustom() {
		val person = CustomPerson(NameImpl("ara"), 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<CustomPerson>(json)
		assertEquals(person, person2)
	}

	@Test
	fun testCustom_withObject() {
		val person = CustomPerson(Anton, 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<CustomPerson>(json)
		assertEquals(person, person2)
	}

	@Test
	fun testObject() {
		val person = ObjectPerson(Anton, 12)
		val json = Json.encodeToString(person)
		val person2 = Json.decodeFromString<ObjectPerson>(json)
		assertEquals(person, person2)
	}
}