package com.greentree.commons.xml;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class XMLElement implements Serializable {

	private final Collection<XMLElement> childrens;
	private final Map<String, String> attributes;
	private final String name, content;
	private static final long serialVersionUID = 1L;

	public XMLElement(Collection<XMLElement> childrens, Map<String, String> attributes, String name, String content) {
		this.childrens = childrens;
		this.attributes = attributes;
		this.name = name;
		this.content = content;
	}

	public String getAttribute(final String name) {
		return attributes.get(name);
	}

	public String getAttribute(final String name, final String def) {
		final String  value = getAttribute(name);
		if(value == null || value.length() == 0) return def;
		return value;
	}

	public String[] getAttributeNames() {
		var names = attributes.keySet();
		return names.toArray(new String[names.size()]);
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public XMLElement getChildren(final String name) {
		final Iterable<XMLElement> xmls = getChildrens(name);
		for(var xml : xmls)
			return xml;
		return null;
	}

	public Collection<XMLElement> getChildrens() {
		return childrens;
	}

	public Collection<XMLElement> getChildrens(final String name) {
		return getChildrens().parallelStream().filter(xml -> xml.getName().equals(name)).collect(Collectors.toList());
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("<").append(name);
		
		for(final String n : getAttributeNames()) res.append(" ").append(n).append("=\"").append(getAttribute(n)).append("\"");
		
		if(getChildrens().isEmpty()) {
			if(getContent().isBlank())
				res.append("/>");
			else {
				res.append(">");
				if(getContent().indexOf('\n') != -1)
					res.append("\n\t").append(getContent()).append("\n");
				else
					res.append(getContent());
				res.append("</").append(name).append(">");
			}
		}else {
			res.append(">");
			if(getContent().indexOf('\n') != -1)
				res.append("\n\t").append(getContent()).append("\n");
			else
				res.append(getContent());
			for(final XMLElement e : getChildrens()) res.append(("\n"+e.toString()).replace("\n", "\n\t"));
			res.append("\n</").append(name).append(">");
		}
		return res.toString();
	}

}
