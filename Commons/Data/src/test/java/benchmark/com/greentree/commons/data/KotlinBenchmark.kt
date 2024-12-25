package benchmark.com.greentree.commons.data

import com.greentree.commons.data.resource.Resources.of
import com.greentree.commons.data.resource.readTextAsync
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.infra.Blackhole
import java.nio.file.Path

fun FileResource_readTextAsync(file1: Path, file2: Path, blackhole: Blackhole) = readAny(file1, file2, blackhole) {
	of(it).readTextAsync()
}

fun readAny(file1: Path, file2: Path, blackhole: Blackhole, block: suspend (Path) -> Any) = runBlocking {
	val text1 = async {
		block(file1)
	}
	val text2 = async {
		block(file2)
	}
	blackhole.consume(text1.await())
	blackhole.consume(text2.await())
}