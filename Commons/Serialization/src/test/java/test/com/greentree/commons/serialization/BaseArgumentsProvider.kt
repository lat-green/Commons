package test.com.greentree.commons.serialization

import com.greentree.commons.reflection.info.ParameterizedTypeInfo
import com.greentree.commons.reflection.info.TypeInfo
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
				TestData(true, 1),
				TestData(false, 1),
				TestData("Hello", 7),
				TestData(Person(ConstName("Anton")), 8),
				TestData(Person(ValueName(ConstValue("Anton"))), 38),
				TestData(55, 4),
				TestData(Unit, 0),
//				TestData(Box(Object()), 0), as Class<T?>
				TestData(TextBox("Hi"), 4),
				TestData(BitSet().apply {
					repeat(5) {
						val index = java.util.concurrent.ThreadLocalRandom.current().nextInt(100)
						flip(index)
					}
				}, /* size = */ 4 + /* data = */ 13),
				TestData(Box(intArrayOf()), 4),
				TestData(Box(intArrayOf(1, 2, 3)), 16),
				TestData(Box(arrayOf<Int>()), 4),
				TestData(Box(arrayOf<Int>(1, 2, 3)), 16),
				TestData(Box(arrayOf("aaa", 2, Unit)), 22),
				TestData(Box(arrayOf<String>("A", "B", "C")), 13),
				TestData(mutableMapOf("a" to 1, "b" to 2), 30),
				TestData(mutableListOf("a" to 1, "b" to 2), 42),
				TestData(mutableSetOf("a" to 1, "b" to 2), 42),
				TestData(mutableListOf("a", "b"), 16),
				TestData(
					mutableMapOf("a" to 1, "b" to 2), 18, ParameterizedTypeInfo.fromClass<Map<String, Int>>(
						String::class,
						Integer::class
					)
				),
				TestData(
					mutableListOf("a" to 1, "b" to 2), 30, ParameterizedTypeInfo.fromClass<List<Pair<String, Int>>>(
						ParameterizedTypeInfo.fromClass<Pair<String, Int>>(
							String::class,
							Integer::class
						),
					)
				),
				TestData(
					mutableSetOf("a" to 1, "b" to 2), 30, ParameterizedTypeInfo.fromClass<Set<Pair<String, Int>>>(
						ParameterizedTypeInfo.fromClass<Pair<String, Int>>(
							String::class,
							Integer::class
						),
					)
				),
				TestData(
					mutableListOf("a", "b"), 10, ParameterizedTypeInfo.fromClass<List<String>>(
						String::class,
					)
				),
			),
			(0 .. ((intAccuracy.max - intAccuracy.min + intAccuracy.accuracy / 2) / intAccuracy.accuracy).toInt())
				.map { TestData(IntBox(it * intAccuracy.accuracy + intAccuracy.min), 1) }
				.stream(),
			(0 .. ((floatAccuracy.max - floatAccuracy.min + floatAccuracy.accuracy / 2) / floatAccuracy.accuracy).toInt())
				.map { TestData(FloatBox(it * floatAccuracy.accuracy + floatAccuracy.min), 2) }
				.stream(),
		).flatMap { it }.parallel().map {
			Arguments.of(it)
		}
	}
}

data class TestData<T : Any>(
	val value: T,
	val maxByteSize: Int = Int.MAX_VALUE,
	val type: TypeInfo<out T> = TypeInfo(value::class),
)