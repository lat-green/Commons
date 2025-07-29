package benchmark.com.greentree.commons.data

import com.greentree.commons.data.resource.Resources
import com.greentree.commons.data.resource.readBytesAsync
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.infra.Blackhole
import java.nio.file.Path

fun FileResource_readBytesAsync(files: List<Path>, blackhole: Blackhole) = runBlocking {
	for(deferred in files.asSequence()
		.map { Resources.of(it) }
		.map {
			async {
				it.readBytesAsync()
			}
		}.toList()) {
		blackhole.consume(deferred.await())
	}
}
