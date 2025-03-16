package test.com.greentree.commons.serialization

import com.greentree.commons.serialization.serializator.accuracy.FloatAccuracy
import com.greentree.commons.serialization.serializator.accuracy.IntAccuracy
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import test.com.greentree.commons.serialization.SerializationTest.*
import java.util.*
import java.util.stream.Stream

data object BaseArgumentsProvider : ArgumentsProvider {

	override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
		val intAccuracy = IntBox::class.java.getDeclaredField("value")
			.getAnnotation(IntAccuracy::class.java)!!
		val floatAccuracy = FloatBox::class.java.getDeclaredField("value")
			.getAnnotation(FloatAccuracy::class.java)!!
		return Stream.of(
			Stream.of(
				Arguments.of(true, 1),
				Arguments.of(false, 1),
				Arguments.of("Hello", 7),
				Arguments.of(Person(ConstName("Anton")), 8),
				Arguments.of(Person(ValueName(ConstValue("Anton"))), 38),
				Arguments.of(55, 4),
				Arguments.of(Unit, 0),
//				Arguments.of(Box(Object()), 0),
				Arguments.of(TextBox("Hi")),
				Arguments.of(BitSet().apply {
					repeat(5) {
						val index = java.util.concurrent.ThreadLocalRandom.current().nextInt(100)
						flip(index)
					}
				}, /* size = */ 4 + /* data = */ 13),
				Arguments.of(Box(intArrayOf()), 4),
				Arguments.of(Box(intArrayOf(1, 2, 3)), 16),
				Arguments.of(Box(arrayOf<Int>()), 4),
				Arguments.of(Box(arrayOf<Int>(1, 2, 3)), 16),
				Arguments.of(Box(arrayOf("aaa", 2, Unit)), 22),
				Arguments.of(Box(arrayOf<String>("A", "B", "C")), 13),
			),
			(0 .. ((intAccuracy.max - intAccuracy.min + intAccuracy.accuracy / 2) / intAccuracy.accuracy).toInt())
				.map { Arguments.of(IntBox(it * intAccuracy.accuracy + intAccuracy.min), 1) }
				.stream(),
			(0 .. ((floatAccuracy.max - floatAccuracy.min + floatAccuracy.accuracy / 2) / floatAccuracy.accuracy).toInt())
				.map { Arguments.of(FloatBox(it * floatAccuracy.accuracy + floatAccuracy.min), 2) }
				.stream(),
		).flatMap { it }.parallel().map {
			val values = it.get()
			if(values.size < 2)
				Arguments.of(values[0], Int.MAX_VALUE)
			else
				it
		}
	}
}