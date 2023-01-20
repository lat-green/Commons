package com.greentree.commons.xml.parser;

import java.io.IOException;
import java.io.InputStream;

import com.greentree.commons.xml.XMLElement;

public class LXMLParser {

	private static final XMLParser PARSER = new XMLParser();
	private static final XMLLexer  LEXER  = new XMLLexer();
	
	public static XMLElement parse(InputStream input) throws IOException {
		final String str;
		try(input) {
			str = new String(input.readAllBytes());
		}
		return parse(str);
	}

	public static XMLElement parse(final String text) throws IOException {
		final var toc = LEXER.scan(text);
		return PARSER.parse(toc);
	}
	
}
