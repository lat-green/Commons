package test.com.greentree.commons.data.resource

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.SystemFileResource
import com.greentree.commons.data.resource.readText
import com.greentree.commons.data.resource.readTextAsync
import com.greentree.commons.data.resource.writeBytes
import com.greentree.commons.data.resource.writeText
import com.greentree.commons.tests.aop.AutowiredArgument
import com.greentree.commons.tests.aop.AutowiredConfig
import com.greentree.commons.tests.aop.AutowiredTest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.Test

@AutowiredConfig(ResourceTestConfig::class)
class ResourceTest {

	@AutowiredTest()
	fun isExists(@AutowiredArgument(tags = ["exists", "file"]) resource: FileResource) {
		assertTrue(resource.exists())
	}

	@AutowiredTest()
	fun read(@AutowiredArgument(tags = ["exists", "file"]) resource: FileResource) {
		resource.open().use {
			assertNotNull(it)
		}
	}

	@Test
	fun readFileAsync() {
		val file = SystemFileResource.crateTempResource("junit", ".txt")
		val size = (DEFAULT_BUFFER_SIZE * 5 + 2 * DEFAULT_BUFFER_SIZE * Math.random()).toInt()
		val bytes = ByteArray(size)
		ThreadLocalRandom.current().nextBytes(bytes)
		file.writeBytes(bytes)
		val data1 = file.readText()
		val data2 = runBlocking {
			file.readTextAsync()
		}
		assertEquals(data1, data2)
	}

	@AutowiredTest()
	fun readAll(@AutowiredArgument(tags = ["exists", "file"]) resource: FileResource) {
		assertNotNull(resource.readText())
	}

	@AutowiredTest()
	fun writeAndRead(@AutowiredArgument(tags = ["file"]) resource: MutableFileResource) {
		val expected = (0 .. 10).joinToString(" ") { UUID.randomUUID().toString() }
		resource.writeText(expected)
		val actual = resource.readText()
		assertEquals(expected, actual)
	}

	@AutowiredTest()
	fun writeChannelAndRead(@AutowiredArgument(tags = ["file"]) resource: MutableFileResource) {
		val expected = (0 .. 10).joinToString(" ") { UUID.randomUUID().toString() }
		resource.openWriteChannel().use { c ->
			c.writeText(expected)
		}
		val actual = resource.readText()
		assertEquals(expected, actual)
	}

	@AutowiredTest()
	fun writeAndReadChannel(@AutowiredArgument(tags = ["file"]) resource: MutableFileResource) {
		val expected = UUID.randomUUID().toString()
		resource.writeText(expected)
		val actual = resource.openChannel().use { c ->
			c.readText()
		}
		assertEquals(expected, actual)
	}

	@AutowiredTest()
	fun writeAndReadLastModified(@AutowiredArgument(tags = ["file"]) resource: MutableFileResource) {
		val expected = (0 .. 10).joinToString(" ") { UUID.randomUUID().toString() }
		val m1 = resource.lastModified()
		resource.writeText(expected)
		val m2 = resource.lastModified()
		assertTrue(m2 > m1)
	}
}
