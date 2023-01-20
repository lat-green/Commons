package com.greentree.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.util.cortege.Pair;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.commons.xml.parser.XMLScanner;
import com.greentree.commons.xml.parser.XMLTocen;
import com.greentree.commons.xml.parser.XMLTocenType;

public class XMLScannerTest {

	static Stream<Pair<String, ArrayList<XMLTocen>>> xmls() throws IOException {
		final Collection<Pair<String, ArrayList<XMLTocen>>> streams = new ArrayList<>();

		{
			final var a = new ArrayList<XMLTocen>();
			a.add(new XMLTocen("<body", XMLTocenType.BEGIN_TAG));
			a.add(new XMLTocen("/>", XMLTocenType.END_TAG_C));
    		streams.add(new Pair<>("""
    			<body/>
    			""", a));
		}
		{
			final var a = new ArrayList<XMLTocen>();
			a.add(new XMLTocen("<body", XMLTocenType.BEGIN_TAG));
			a.add(new XMLTocen("name=", XMLTocenType.ATR_NAME));
			a.add(new XMLTocen("\"ara\"", XMLTocenType.ATR_VALUE));
			a.add(new XMLTocen("/>", XMLTocenType.END_TAG_C));
    		streams.add(new Pair<>("""
    			<body name="ara"/>
    			""", a));
		}
		
		return streams.stream();
	}

	@ParameterizedTest
	@MethodSource("xmls")
	void test1(Pair<String, ArrayList<XMLTocen>> p) {
		final var CODE = p.first;
		final ArrayList<XMLTocen> res = new ArrayList<>();
		final var sc = IteratorUtil.filter(new XMLScanner(CODE), t -> t.type() != XMLTocenType.SPASE);
		while(sc.hasNext()) {
			res.add(sc.next());
		}
		assertEquals(res, p.seconde);
	}
	
}
