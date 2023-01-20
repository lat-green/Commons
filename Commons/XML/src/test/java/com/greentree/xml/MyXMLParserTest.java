package com.greentree.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.greentree.commons.xml.MyXMLParser;

public class MyXMLParserTest {

	static Stream<InputStream> xmls() {
		final Collection<InputStream> streams = new ArrayList<>();

		final var cl = MyXMLParserTest.class.getClassLoader();

		int i = 0;
		InputStream s;
		while((s = cl.getResourceAsStream("XMLs/"+ i+ ".xml")) != null) {
			streams.add(s);
			i++;
		}

		return streams.stream();
	}

	@ParameterizedTest
	@MethodSource("xmls")
	void noExeption(InputStream in) throws IOException {
		try(in) {
			MyXMLParser.parse(in);
		}
	}

}
