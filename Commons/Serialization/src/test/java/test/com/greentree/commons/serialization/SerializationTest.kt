package test.com.greentree.commons.serialization

import com.greentree.commons.context.BeanContext
import com.greentree.commons.context.layer.registerLayer
import com.greentree.commons.context.resolveBean
import com.greentree.commons.serialization.Serialization
import com.greentree.commons.serialization.serializator.accuracy.FloatAccuracy
import com.greentree.commons.serialization.serializator.accuracy.IntAccuracy
import com.greentree.commons.serialization.serializator.manager.SerializatorManager

abstract class SerializationTest {

	protected val manager: SerializatorManager = BeanContext().apply {
		registerLayer(Serialization)
	}.resolveBean()

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
