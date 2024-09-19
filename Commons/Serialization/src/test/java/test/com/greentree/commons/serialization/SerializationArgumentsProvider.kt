package test.com.greentree.commons.serialization

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.*
import org.junit.jupiter.params.provider.ArgumentsProvider
import test.data.Anton
import test.data.CustomPerson
import test.data.NameImpl
import test.data.NamePerson
import test.data.ObjectPerson
import test.data.OpenPerson
import test.data.Person
import test.data.PersonImpl
import test.data.Status
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.Stream

object SerializationArgumentsProvider : ArgumentsProvider {

	override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
		val random = ThreadLocalRandom.current()
		fun nextInt() = random.nextInt()
		return Stream.of(
			of(Unit),
			of(true),
			of(false),
			of(nextInt().toByte()),
			of(nextInt().toChar()),
			of(nextInt().toShort()),
			of(nextInt().toInt()),
			of(nextInt().toLong()),
			of(nextInt().toFloat() + .5f),
			of(nextInt().toDouble() + .5),
			of(""),
			of("Hello"),
			of(CustomPerson(NameImpl("ara"), 12)),
			of(ObjectPerson(Anton, 12)),
			of(CustomPerson(Anton, 12)),
			of(PersonImpl(NameImpl("ara"), 12)),
			of(NamePerson(NameImpl("ara"), 12)),
			of(Person("ara", 12)),
			of(Status.CANCELLED),
			of(OpenPerson("ara", 12)),
			of(listOf("Hello", nextInt())),
			of(setOf("Hello", nextInt())),
//			of(mapOf("a" to nextInt(), "b" to nextInt()))
		)
	}
}