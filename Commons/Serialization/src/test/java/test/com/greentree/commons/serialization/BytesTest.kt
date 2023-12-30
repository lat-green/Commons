package test.com.greentree.commons.serialization.test.com.greentree.commons.serialization

import com.greentree.commons.serialization.data.Bytes
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import test.data.NameImpl
import test.data.NamePerson
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
}
