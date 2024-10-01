package test.com.greentree.commons.data.resource

import com.greentree.commons.data.resource.location.CacheResourceLocation
import com.greentree.commons.data.resource.location.RootFileResourceLocation
import com.greentree.commons.data.resource.location.RootUrlResourceLocation
import com.greentree.commons.data.resource.readText
import com.greentree.commons.tests.DisabledIfInternetNotConnected
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.time.Duration

internal class CacheFolderResourceLocationTest {

	@Test
	@Throws(IOException::class)
	fun test1() {
		val inFolder = Files.createTempDirectory("in").toFile()
		val outFolder = Files.createTempDirectory("out").toFile()
		inFolder.deleteOnExit()
		outFolder.deleteOnExit()
		val a = File(inFolder, "a.txt")
		a.writeText(TEXT)
		val inLocation = RootFileResourceLocation(inFolder)
		val location = CacheResourceLocation(inLocation, RootFileResourceLocation(outFolder))
		val res = location.getFileResource("a.txt")
		assertEquals(TEXT, res.readText())
	}

	@DisabledIfInternetNotConnected
	@Test
	@Throws(IOException::class)
	fun test2() {
		val out_dir = Files.createTempDirectory("out").toFile()
		out_dir.deleteOnExit()
		val `in` = RootUrlResourceLocation(
			URL("https://avatars.githubusercontent.com/u/")
		)
		val out = RootFileResourceLocation(out_dir)
		val cache = CacheResourceLocation(`in`, out)
		assertTimeout(Duration.ofMillis(5000)) {
			cache.getFileResource("30156490")
		}
		assertTimeout(Duration.ofMillis(500)) {
			cache.getFileResource("30156490")
		}
		out_dir.delete()
	}

	companion object {

		private const val TEXT = "Hello"
	}
}
