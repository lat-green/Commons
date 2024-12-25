package test.com.greentree.commons.data

import com.greentree.commons.data.resource.Resources
import com.greentree.commons.data.resource.readTextAsync
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Files

class ResourceAsyncTest {

	@Test
	fun test1() {
		val file = Files.createTempFile("test1", ".txt").toFile()
		val text = "Hello"
		file.writer().use {
			it.write(text)
		}
		val t1 = runBlocking {
			Resources.of(file).readTextAsync()
		}
		assertEquals(t1, text)
		file.delete()
	}
}