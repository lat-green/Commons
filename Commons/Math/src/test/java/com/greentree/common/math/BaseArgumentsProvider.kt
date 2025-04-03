package com.greentree.common.math

import org.joml.Vector2f
import org.joml.times
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream
import kotlin.random.Random.Default.nextFloat
import kotlin.streams.asStream

data object BaseArgumentsProvider : ArgumentsProvider {

	override fun provideArguments(context: ExtensionContext): Stream<out Arguments> {
		val count = 1
		val a = 100f
		return sequence {
			repeat(count) {
//				yield(Arguments.of(Quaternionf(nextFloat(), nextFloat(), nextFloat(), nextFloat()) * a, 16))
//				yield(Arguments.of(Vector4f(nextFloat(), nextFloat(), nextFloat(), nextFloat()) * a, 16))
//				yield(Arguments.of(Vector3f(nextFloat(), nextFloat(), nextFloat()) * a, 12))
				yield(Arguments.of(Vector2f(nextFloat(), nextFloat()) * a, 8))
			}
		}.map {
			val values = it.get()
			if(values.size < 2)
				Arguments.of(values[0], Int.MAX_VALUE)
			else
				it
		}.asStream()
	}
}