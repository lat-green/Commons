package benchmark.com.greentree.commons.xml;

import com.greentree.commons.xml.parser.ANTLR4XMLParser;
import com.greentree.commons.xml.parser.MyXMLParser;
import com.greentree.commons.xml.parser.SAXXMLParser;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 2)
@Warmup(iterations = 2, time = 2)
@Fork(2)
public class Parsers {

    private String text;

    @Param({"0", "1", "2", "3", "4", "5", "6", "7", "8"})
    private String name;

    @Setup
    public void init() throws Exception {
        try (var stream = new FileInputStream("src/test/resources/XMLs/" + name + ".xml")) {
            text = new String(stream.readAllBytes());
        }
    }

    @Benchmark
    public void benchmarkANTLR4XMLParser(Blackhole blackhole) throws Throwable {
        var xml = ANTLR4XMLParser.INSTANCE.parse(text);
        blackhole.consume(xml);
    }

    @Benchmark
    public void benchmarkMyXMLParser(Blackhole blackhole) throws Throwable {
        var xml = MyXMLParser.INSTANCE.parse(text);
        blackhole.consume(xml);
    }

    @Benchmark
    public void benchmarkSAXXMLParser(Blackhole blackhole) throws Throwable {
        try (var stream = new ByteArrayInputStream(text.getBytes())) {
            var xml = SAXXMLParser.INSTANCE.parse(stream);
            blackhole.consume(xml);
        }
    }

}


