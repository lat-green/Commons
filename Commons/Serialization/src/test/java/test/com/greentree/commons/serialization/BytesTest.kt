package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Bytes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import test.data.Anton
import test.data.CustomPerson
import test.data.NameImpl
import test.data.NamePerson
import test.data.ObjectPerson
import test.data.Person
import test.data.PersonImpl
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BytesTest {

	@Test
	fun testOnlyPrimitives() {
		val person = Person("ara", 12)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(person, stream)
			stream.toByteArray()
		}
		val person2 = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<Person>(stream)
		}
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testSealedClass() {
		val person = NamePerson(NameImpl("ara"), 12)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(person, stream)
			stream.toByteArray()
		}
		val person2 = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<NamePerson>(stream)
		}
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testPrimitive() {
		val value = 12
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(value, stream)
			stream.toByteArray()
		}
		val decodeValue = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<Int>(stream)
		}
		Assertions.assertEquals(value, decodeValue)
	}

	@Test
	fun testFinalClass() {
		val person = PersonImpl(NameImpl("ara"), 12)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(person, stream)
			stream.toByteArray()
		}
		val person2 = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<PersonImpl>(stream)
		}
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testCustom_withObject() {
		val person = CustomPerson(Anton, 12)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(person, stream)
			stream.toByteArray()
		}
		val person2 = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<CustomPerson>(stream)
		}
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testObject() {
		val person = ObjectPerson(Anton, 12)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(person, stream)
			stream.toByteArray()
		}
		val person2 = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<ObjectPerson>(stream)
		}
		Assertions.assertEquals(person, person2)
	}

	@Test
	fun testCustom() {
		val person = CustomPerson(NameImpl("ara"), 12)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(person, stream)
			stream.toByteArray()
		}
		val person2 = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<CustomPerson>(stream)
		}
		Assertions.assertEquals(person, person2)
	}

	@Disabled
	@Test
	fun reflectCollection() {
		val list = mutableListOf<Any>()
		list.add(list)
		val arr = ByteArrayOutputStream().use { stream ->
			Bytes.encodeToSteam(list, stream)
			stream.toByteArray()
		}
		val deserializeList = ByteArrayInputStream(arr).use { stream ->
			Bytes.decodeFromSteam<CustomPerson>(stream)
		}
		Assertions.assertEquals(list, deserializeList)
	}
}
