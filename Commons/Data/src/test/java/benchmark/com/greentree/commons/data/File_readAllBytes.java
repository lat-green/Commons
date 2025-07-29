package benchmark.com.greentree.commons.data;

import com.greentree.commons.data.AsynchronousFileInputStream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 1)
@Warmup(iterations = 1, time = 1)
@Fork(1)
public class File_readAllBytes {

    private static final int SIZE_KB = 1024;
    private static final int SIZE_MB = SIZE_KB * SIZE_KB;
    @Param({"" + SIZE_MB, "" + 100 * SIZE_MB, "" + 500 * SIZE_MB})
    private int size;
    private Path file;

    @Setup
    public void init() throws Exception {
        file = Files.createTempFile("testFileRead", ".txt");
        byte[] bytes = new byte[size];
        ThreadLocalRandom.current().nextBytes(bytes);
        Files.write(file, bytes);
    }

    @Benchmark
    public void FileChannel_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = Channels.newInputStream(FileChannel.open(file))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void BufferedInputStream_FileChannel_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = new BufferedInputStream(Channels.newInputStream(FileChannel.open(file)))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void FileInputStream_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = new FileInputStream(file.toFile())) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void BufferedInputStream_FileInputStream_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = new BufferedInputStream(new FileInputStream(file.toFile()))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @TearDown
    public void clear() throws Exception {
        Files.delete(file);
    }

    @Benchmark
    public void Files_readAllBytes(Blackhole blackhole) throws Exception {
        byte[] bytes = Files.readAllBytes(file);
        blackhole.consume(bytes);
    }

    @Benchmark
    public void AsynchronousFileInputStream_readAllBytes(Blackhole blackhole) throws Exception {
        try (var in = new AsynchronousFileInputStream(file)) {
            byte[] bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

}


