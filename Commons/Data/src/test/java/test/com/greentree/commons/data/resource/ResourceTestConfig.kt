package test.com.greentree.commons.data.resource

import com.greentree.commons.data.resource.InMemoryFileResource
import com.greentree.commons.data.resource.MutableFileResource
import com.greentree.commons.data.resource.MutableFolderResource
import com.greentree.commons.data.resource.Resources
import com.greentree.commons.tests.aop.AutowiredArgument
import com.greentree.commons.tests.aop.AutowiredProvider
import java.nio.file.Files

class ResourceTestConfig {

	@AutowiredProvider(tags = ["file"])
	fun emptyInMemoryFileResource() = InMemoryFileResource("text.txt")

	@AutowiredProvider(tags = ["exists", "file"])
	fun existsInMemoryFileResource() = InMemoryFileResource("text.txt", "Hello".toByteArray())

	@AutowiredProvider(tags = ["exists", "file"])
	fun createTempFile() = Resources.of(Files.createTempFile("data", ".txt"))

	@AutowiredProvider(tags = ["file"])
	fun createTempFile(@AutowiredArgument(tags = ["exists", "folder"]) folder: MutableFolderResource) =
		folder.getChildren("test.txt") as MutableFileResource

	@AutowiredProvider(tags = ["exists", "folder"])
	fun createTempDirectory() = Resources.of(Files.createTempDirectory("data"))
}
