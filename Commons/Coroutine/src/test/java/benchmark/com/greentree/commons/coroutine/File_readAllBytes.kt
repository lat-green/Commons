package benchmark.com.greentree.commons.coroutine

import com.greentree.commons.coroutine.AsynchronousFileInputStream
import kotlinx.coroutines.runBlocking
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 2)
@Warmup(iterations = 2, time = 2)
@Fork(2)
open class File_readAllBytes {

	@Param("" + SIZE_MB, "" + 100 * SIZE_MB, "" + 500 * SIZE_MB)
	private val size = 0
	private lateinit var file: Path

	@Setup
	fun init() {
		file = Files.createTempFile("testFileRead", ".txt")
		val bytes = ByteArray(size)
		ThreadLocalRandom.current().nextBytes(bytes)
		Files.write(file, bytes)
	}

	@TearDown
	fun clear() {
		Files.delete(file)
	}

	@Benchmark
	fun Files_readAllBytes(blackhole: Blackhole) {
		val bytes = Files.readAllBytes(file)
		blackhole.consume(bytes)
	}

	//    @Benchmark
	//    public void FileChannel_readAllBytes(Blackhole blackhole) throws IOException {
	//        try (var in = Channels.newInputStream(FileChannel.open(file))) {
	//            var bytes = in.readAllBytes();
	//            blackhole.consume(bytes);
	//        }
	//    }
	//
	//    @Benchmark
	//    public void BufferedInputStream_FileChannel_readAllBytes(Blackhole blackhole) throws IOException {
	//        try (var in = new BufferedInputStream(Channels.newInputStream(FileChannel.open(file)))) {
	//            var bytes = in.readAllBytes();
	//            blackhole.consume(bytes);
	//        }
	//    }
	//
	//    @Benchmark
	//    public void FileInputStream_readAllBytes(Blackhole blackhole) throws IOException {
	//        try (var in = new FileInputStream(file.toFile())) {
	//            var bytes = in.readAllBytes();
	//            blackhole.consume(bytes);
	//        }
	//    }
	//
	//    @Benchmark
	//    public void BufferedInputStream_FileInputStream_readAllBytes(Blackhole blackhole) throws IOException {
	//        try (var in = new BufferedInputStream(new FileInputStream(file.toFile()))) {
	//            var bytes = in.readAllBytes();
	//            blackhole.consume(bytes);
	//        }
	//    }
	@Benchmark
	fun AsynchronousFileInputStream_readAllBytes(blackhole: Blackhole) = runBlocking {
		AsynchronousFileInputStream(file).use { `in` ->
			val bytes = `in`.readAllBytes()
			blackhole.consume(bytes)
		}
	}

	companion object {

		private const val SIZE_KB = 1024
		private const val SIZE_MB = SIZE_KB * SIZE_KB
	}
}
