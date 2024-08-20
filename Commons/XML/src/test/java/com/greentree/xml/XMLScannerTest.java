package com.greentree.xml;

public class XMLScannerTest {
//    static Stream<Pair<String, ArrayList<XMLTocen>>> xmls() throws IOException {
//        final Collection<Pair<String, ArrayList<XMLTocen>>> streams = new ArrayList<>();
//        {
//            final var a = new ArrayList<XMLTocen>();
//            a.add(new XMLTocen("<body", XMLTocenType.BEGIN_TAG));
//            a.add(new XMLTocen("/>", XMLTocenType.END_TAG_C));
//            streams.add(new Pair<>("""
//                    <body/>
//                    """, a));
//        }
//        {
//            final var a = new ArrayList<XMLTocen>();
//            a.add(new XMLTocen("<body", XMLTocenType.BEGIN_TAG));
//            a.add(new XMLTocen("name=", XMLTocenType.ATR_NAME));
//            a.add(new XMLTocen("\"ara\"", XMLTocenType.ATR_VALUE));
//            a.add(new XMLTocen("/>", XMLTocenType.END_TAG_C));
//            streams.add(new Pair<>("""
//                    <body name="ara"/>
//                    """, a));
//        }
//        return streams.stream();
//    }
//
//    @ParameterizedTest
//    @MethodSource("xmls")
//    void test1(Pair<String, ArrayList<XMLTocen>> p) {
//        final var CODE = p.first;
//        final ArrayList<XMLTocen> res = new ArrayList<>();
//        final var sc = IteratorUtil.filter(new XMLScanner(CODE), t -> t.getType() != XMLTocenType.SPASE);
//        while (sc.hasNext()) {
//            res.add(sc.next());
//        }
//        assertEquals(res, p.seconde);
//    }

}
