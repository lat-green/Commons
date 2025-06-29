package test.com.greentree.commons.data

import com.greentree.commons.data.resource.Resources
import com.greentree.commons.data.resource.readBytesAsync
import com.greentree.commons.data.resource.readTextAsync
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Files

class ResourceAsyncTest {

	@Test
	fun readTextAsync() {
		val file = Files.createTempFile("readTextAsync", ".txt").toFile()
		file.deleteOnExit()
		val actualText = "Hello"
		file.writer().use {
			it.write(actualText)
		}
		val expectedText = runBlocking {
			Resources.of(file).readTextAsync()
		}
		assertEquals(expectedText, actualText)
	}

	@Test
	fun readBytesAsync() {
		val file = Files.createTempFile("readBytesAsync", ".txt").toFile()
		file.deleteOnExit()
		val actualBytes = "Hello".toByteArray()
		file.outputStream().use {
			it.write(actualBytes)
		}
		val expectedBytes = runBlocking {
			Resources.of(file).readBytesAsync()
		}

		assertArrayEquals(expectedBytes, actualBytes)
	}
}