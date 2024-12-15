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
@Measurement(iterations = 2, time = 2)
@Warmup(iterations = 2, time = 2)
@Fork(2)
public class TwoFile_readAllBytes {

    private static final int SIZE_KB = 1024;
    private static final int SIZE_MB = SIZE_KB * SIZE_KB;
    @Param({"" + SIZE_MB, "" + 20 * SIZE_MB, "" + 100 * SIZE_MB, "" + 500 * SIZE_MB})
    private int size;

    private Path file1;
    private Path file2;

    @Setup
    public void init() throws Exception {
        file1 = Files.createTempFile("testFileRead1", ".txt");
        file2 = Files.createTempFile("testFileRead2", ".txt");
        byte[] bytes = new byte[size];
        ThreadLocalRandom.current().nextBytes(bytes);
        Files.write(file1, bytes);
        ThreadLocalRandom.current().nextBytes(bytes);
        Files.write(file2, bytes);
    }

    @TearDown
    public void clear() throws Exception {
        Files.delete(file1);
        Files.delete(file2);
    }

    @Benchmark
    public void FileResource_readTextAsync(Blackhole blackhole) throws IOException {
        KotlinBenchmarkKt.FileResource_readTextAsync(file1, file2, blackhole);
    }

    @Benchmark
    public void FileChannel_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = Channels.newInputStream(FileChannel.open(file1))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
        try (var in = Channels.newInputStream(FileChannel.open(file2))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void BufferedInputStream_FileChannel_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = new BufferedInputStream(Channels.newInputStream(FileChannel.open(file1)))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
        try (var in = new BufferedInputStream(Channels.newInputStream(FileChannel.open(file2)))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void FileInputStream_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = new FileInputStream(file1.toFile())) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
        try (var in = new FileInputStream(file2.toFile())) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void BufferedInputStream_FileInputStream_readAllBytes(Blackhole blackhole) throws IOException {
        try (var in = new BufferedInputStream(new FileInputStream(file1.toFile()))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
        try (var in = new BufferedInputStream(new FileInputStream(file2.toFile()))) {
            var bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

    @Benchmark
    public void Files_readAllBytes(Blackhole blackhole) throws Exception {
        byte[] bytes1 = Files.readAllBytes(file1);
        blackhole.consume(bytes1);
        byte[] bytes2 = Files.readAllBytes(file2);
        blackhole.consume(bytes2);
    }

    @Benchmark
    public void AsynchronousFileInputStream_readAllBytes(Blackhole blackhole) throws Exception {
        try (var in = new AsynchronousFileInputStream(file1)) {
            byte[] bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
        try (var in = new AsynchronousFileInputStream(file2)) {
            byte[] bytes = in.readAllBytes();
            blackhole.consume(bytes);
        }
    }

}


