package com.greentree.commons.xml.parser;

import java.util.regex.Pattern;

public enum XMLTocenType {
	
	PBEGIN_TAG("<\\?[a-zA-Z_]\\w*"),
	BEGIN_TAG("<[a-zA-Z_]\\w*"),
	BEGIN_TAG_C("<\\/[a-zA-Z_]\\w*>"),
	PEND_TAG("\\?>"),
	END_TAG_C("\\/>"),
	END_TAG(">"),
	ATR_NAME("[a-zA-Z_]\\w*="),
	SPASE(" "),
	ATR_VALUE("\"\\S*\""),
	TEXT("[^<>]*\\w[^<>]*"),
	;
    	
	private final String regex;

	XMLTocenType(String regex) {
		this.regex = regex;
	}

	public Matcher find(int position, String str) {
		final var pattern = Pattern.compile(regex);
		final var m = pattern.matcher(str);
		if(m.find(position) && m.start() == position)
			return new Matcher(m.start(), m.end());
		return null;
	}
	
	public record Matcher(int start, int end) {
		
		public Matcher(int start, int end) {
			if(start > end)
				throw new IllegalArgumentException();
			this.start = start;
			this.end = end;
		}
		
		public String substring(String str) {
			return str.substring(start,  end);
		}
		
	}
	
	
}