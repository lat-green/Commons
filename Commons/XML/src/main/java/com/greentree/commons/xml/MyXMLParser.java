package com.greentree.commons.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.greentree.commons.util.cortege.Pair;

public class MyXMLParser {

	public static XMLElement parse(InputStream input) throws IOException {
		final String str;
		try(input) {
			str = new String(input.readAllBytes());
		}
		return parse(str);
	}
	public static XMLElement parse(final String text) throws IOException {

		final var stack = new Stack<BuilderXMLElement>();

		stack.push(new BuilderXMLElement());

		int le = -1;
		int s = text.indexOf('<');
		while(s != -1) {

			final int e;
			if(text.charAt(s+1) == '!')
				e = text.indexOf("-->", s) + 2;
			else {
				e = text.indexOf('>', s);

				if(text.charAt(s + 1) != '?' || text.charAt(e - 1) != '?') if(text.charAt(e - 1) == '/') {
					if(text.charAt(s+1) == '/') throw new IllegalArgumentException(text.substring(s+1, e));

					final var name = text.substring(s + 1, e-1);

					stack.peek().childrens.add(new BuilderXMLElement(name).build());

				}else {
					if(le != -1)
						stack.peek().context.append(text.substring(le+1, s).strip());

					if(text.charAt(s+1) == '/') {
						final var str1 = text.substring(s + 2, e);
						var my = stack.pop();

						if(!str1.equals(my.name)) throw new IllegalArgumentException("not equals open(" + my.name + ") and close(" + str1 + ")");

						stack.peek().childrens.add(my.build());
					} else {
						final var str1 = text.substring(s + 1, e);
						stack.push(new BuilderXMLElement(str1));
					}
				}
			}
			s = text.indexOf('<', e);
			le = e;
		}

		if(stack.size() < 1) throw new IllegalArgumentException("empty file");
		if(stack.size() > 1) throw new IllegalArgumentException("more one xml element is root");
		
		for(var v : stack.peek().childrens)
			return v;
		throw new IllegalArgumentException(text);
	}

	private static Map<String, String> getAttributes(String attributes) {
		Map<String, String> map = new HashMap<>();
		int space = 0;
		while(space != -1) {
			int s = attributes.indexOf('\"', space + 1);
			int e = attributes.indexOf('\"', s + 1);

			var name = attributes.substring(space, attributes.indexOf('=', space + 1));
			var value = attributes.substring(s+1, e);

			map.put(name.strip(), value);

			space = attributes.indexOf(' ', e+1);
		}
		return map;
	}

	private static Pair<String, Map<String, String>> getText(String text) {
		final int i = text.indexOf(' ');

		if(i != -1) {
			final var name = text.substring(0, i);
			final var attributes = text.substring(i);
			return new Pair<>(name, getAttributes(attributes));
		}else return new Pair<>(text, new HashMap<>());

	}

	private final static class BuilderXMLElement {

		public final Collection<XMLElement> childrens = new ArrayList<>();
		public final Map<String, String> attributes;
		public final String name;
		public final StringBuilder context = new StringBuilder();


		public BuilderXMLElement() {
			this(null, new HashMap<>());
		}

		public BuilderXMLElement(Pair<String, Map<String, String>> text) {
			this(text.first, text.seconde);
		}

		public BuilderXMLElement(String name) {
			this(getText(name));
		}

		public BuilderXMLElement(String name, Map<String, String> attributes) {
			this.name = name;
			this.attributes = attributes;
		}

		@Override
		public String toString() {
			String str = "";
			if(!childrens.isEmpty()) str += " childrens=" + childrens;
			if(!attributes.isEmpty()) str += " attributes=" + attributes;
			if(!context.isEmpty()) str += " context=" + context;
			if(str.isEmpty())
				return "BuilderXMLElement [" + name + "]";
			return "BuilderXMLElement [" + name + "] (" + str.strip() + ")";
		}

		private XMLElement build() {
			return new XMLElement(childrens, attributes, name, context.toString());
		}

	}

}
