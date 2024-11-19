package test.com.greentree.commons.xml;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.commons.xml.parser.token.*;
import kotlin.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XMLScannerTest {

    static Stream<Pair<String, ArrayList<XMLTocen>>> xmls() throws IOException {
        final Collection<Pair<String, ArrayList<XMLTocen>>> streams = new ArrayList<>();
        {
            final var a = new ArrayList<XMLTocen>();
            a.add(new BeginOpen("body"));
            a.add(BeginClose.INSTANCE);
            a.add(End.INSTANCE);
            streams.add(new Pair<>("""
                    <body/>
                    """, a));
        }
        {
            final var a = new ArrayList<XMLTocen>();
            a.add(new BeginOpen("body"));
            a.add(new Attribute("name", "ara"));
            a.add(BeginClose.INSTANCE);
            a.add(End.INSTANCE);
            streams.add(new Pair<>("""
                    <body name="ara"/>
                    """, a));
        }
        return streams.stream();
    }

    @ParameterizedTest
    @MethodSource("xmls")
    void test1(Pair<String, ArrayList<XMLTocen>> p) {
        final var CODE = p.getFirst();
        final ArrayList<XMLTocen> res = new ArrayList<>();
        final var sc = IteratorUtil.filter(new XMLScanner(CODE), t -> t != Space.INSTANCE);
        while (sc.hasNext()) {
            res.add(sc.next());
        }
        assertEquals(res, p.getSecond());
    }

}
