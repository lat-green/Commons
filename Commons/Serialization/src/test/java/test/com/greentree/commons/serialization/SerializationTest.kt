package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.Serialization
import com.greentree.commons.serialization.serializator.accuracy.FloatAccuracy
import com.greentree.commons.serialization.serializator.accuracy.IntAccuracy
import com.greentree.commons.serialization.serializator.manager.SerializatorManager
import com.greentree.engine.rex.fuse.tests.ContextTest

@ContextTest(Serialization::class)
abstract class SerializationTest {

	protected lateinit var manager: SerializatorManager

	data class IntBox(
		@IntAccuracy(-100, 100)
		val value: Int,
	)

	data class FloatBox(
		@FloatAccuracy(
			-100f,
			100f,
			0.1f
		)
		val value: Float,
	)

	data class Person(
		val name: Name,
	)

	sealed interface Name {

		val value: String
	}

	interface Value<T> {

		val value: String
	}

	data class ConstName(override val value: String) : Name
	data class ConstValue(override val value: String) : Value<String>
	data class ValueName(val origin: Value<String>) : Name {

		override val value: String
			get() = origin.value
	}
}
