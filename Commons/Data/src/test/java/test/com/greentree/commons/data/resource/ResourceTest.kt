package test.com.greentree.commons.data.resource

import com.greentree.commons.data.resource.FileResource
import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.readText
import com.greentree.commons.data.resource.writeText
import com.greentree.commons.tests.aop.AutowiredArgument
import com.greentree.commons.tests.aop.AutowiredConfig
import com.greentree.commons.tests.aop.AutowiredTest
import org.junit.jupiter.api.Assertions.*
import java.util.*

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
