package com.greentree.common.xml.parser;


public class XMLLexer {
	
	public XMLScanner scan(String code) {
		return new XMLScanner(code);
	}
	
}
