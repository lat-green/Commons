package test.com.greentree.commons.coroutine

import com.greentree.commons.coroutine.AsynchronousFileInputStream
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.ThreadLocalRandom
import java.util.stream.IntStream.*

class AsynchronousFileInputStreamTest {

	private lateinit var path: Path
	private lateinit var bytes: ByteArray

	fun createTempFile(size: Int) {
		path = Files.createTempFile("test_AsynchronousFileInputStreamTest", ".txt")
		bytes = ByteArray(size)
		ThreadLocalRandom.current().nextBytes(bytes)
		Files.write(path, bytes)
	}

	@AfterEach
	fun deleteTempFile() {
		Files.delete(path)
	}

	@MethodSource("sizes")
	@ParameterizedTest
	fun readAllBytes(size: Int) = runBlocking {
		createTempFile(size)
		AsynchronousFileInputStream(path).use {
			val out = it.readAllBytes()
			assertArrayEquals(out, bytes)
		}
	}

	@MethodSource("sizes")
	@ParameterizedTest
	fun read_readAllBytes(size: Int) = runBlocking {
		createTempFile(size)
		AsynchronousFileInputStream(path).use {
			val first = byteArrayOf(it.read().toByte())
			val out = first + it.readAllBytes()
			assertArrayEquals(out, bytes)
		}
	}

	@MethodSource("sizes")
	@ParameterizedTest
	fun read_read(size: Int) = runBlocking {
		createTempFile(size)
		AsynchronousFileInputStream(path).use {
			val a = it.read().toByte()
			val b = it.read().toByte()
			assertEquals(a, bytes[0])
			assertEquals(b, bytes[1])
		}
	}

	companion object {

		@JvmStatic
		fun sizes() = range(1, 64).mapToObj { Arguments.of(it * 1024) }
	}
}