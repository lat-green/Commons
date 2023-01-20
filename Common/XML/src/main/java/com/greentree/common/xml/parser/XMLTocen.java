package com.greentree.common.xml.parser;


public record XMLTocen(String text, XMLTocenType type) {
	
	public String toString() {
		return type+"("+text+")";
	}
	
}
