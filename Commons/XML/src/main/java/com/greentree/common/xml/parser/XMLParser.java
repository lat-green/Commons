package com.greentree.common.xml.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.greentree.common.util.iterator.IteratorUtil;
import com.greentree.common.xml.XMLElement;

public class XMLParser {

	private static int indexOf(List<XMLNode> n, String type) {
		return indexOf(n, type, 0);
	}

	private static int indexOf(List<XMLNode> n, String type, int index) {
		for(int i = index; i < n.size(); i++) if(n.get(i).isType(type))
			return i;
		return -1;
	}

	public XMLElement parse(Iterator<XMLTocen> tocens) {
		final var n = new ArrayList<XMLNode>();
		while(tocens.hasNext()) {
			final var t = tocens.next();
			if(t.type() != XMLTocenType.SPASE)
				n.add(XMLNode.create(t));
		}
		while(find_p(n) || find_tags(n) || find_atrs(n) || find_atr(n) || find_begin_tag_c(n) || find_begin_tag(n) || find_open_tag(n));
		if(n.size() != 1)
			throw new IllegalArgumentException(tocens + " " + n);
		return run(n.get(0));
	}

	private boolean find_atr(List<XMLNode> n) {
		final var index = indexOf(n, XMLNode.getType(XMLTocenType.ATR_NAME));
		if(index != -1) {
			final var atr_name  = n.remove(index);
			final var atr_value = n.remove(index);
			final var atr = new XMLNode(atr_name.name+atr_value.name, "atr");
			n.add(index, atr);
			return true;
		}
		return false;
	}

	private boolean find_atrs(List<XMLNode> n) {
		int index = -1;
		do {
			index = indexOf(n, XMLNode.getType("atr"), index+1);
			if((index == -1) || (index == n.size()-1)) break;
			final var n1 = n.get(index+0);
			final var n2 = n.get(index+1);
			if(n1.isType("atrs") && n2.isType("atrs")) continue;
			if(n2.isType("atr")) {
				n.remove(index);
				n.remove(index);
				final var tag = new XMLNode("", "atrs", n1, n2);
				n.add(index, tag);
				return true;
			}
		} while(true);
		return false;
	}

	private boolean find_begin_tag(List<XMLNode> n) {
		int index = -1;
		do {
			index = indexOf(n, XMLNode.getType(XMLTocenType.BEGIN_TAG), index+1);
			if(index == -1) break;
			if(n.get(index+1).isType(XMLTocenType.END_TAG_C)) {
				final var begin = n.remove(index);
				n.remove(index);
				final var tag = new XMLNode(begin.name.substring(1), "tag");
				n.add(index, tag);
				return true;
			}
			if(n.get(index+1).isType("atr") && n.get(index+2).isType(XMLTocenType.END_TAG_C)) {
				final var begin = n.remove(index);
				final var atr   = n.remove(index);
				n.remove(index);
				final var tag = new XMLNode(begin.name.substring(1), "tag", atr);
				n.add(index, tag);
				return true;
			}

		} while(true);
		return false;
	}

	private boolean find_begin_tag_c(List<XMLNode> n) {
		int index = -1;
		do {
			index = indexOf(n, XMLNode.getType(XMLTocenType.BEGIN_TAG), index+1);
			if(index == -1) break;
			if(n.get(index+1).isType(XMLTocenType.END_TAG)) {
				final var begin = n.remove(index);
				n.remove(index);
				final var tag = new XMLNode(begin.name.substring(1), "open_tag");
				n.add(index, tag);
				return true;
			}
			if(n.get(index+1).isType("atr") && n.get(index+2).isType(XMLTocenType.END_TAG)) {
				final var begin = n.remove(index);
				final var atr   = n.remove(index);
				n.remove(index);
				final var tag = new XMLNode(begin.name.substring(1), "open_tag", atr);
				n.add(index, tag);
				return true;
			}

		} while(true);
		return false;
	}

	private boolean find_open_tag(List<XMLNode> n) {
		int index = -1;
		do {
			index = indexOf(n, XMLNode.getType("open_tag"), index+1);
			if(index == -1) break;
			if(n.get(index+1).isType(XMLTocenType.BEGIN_TAG_C)) {
				final var begin = n.remove(index);
				n.remove(index);
				final var tag = new XMLNode(begin.name.substring(1), "tag");
				n.add(index, tag);
				return true;
			}
			if(n.get(index+1).isType("data") && n.get(index+2).isType(XMLTocenType.BEGIN_TAG_C)) {
				final var begin   = n.remove(index);
				final var sub_tag = n.remove(index);
				n.remove(index);
				final var tag = new XMLNode(begin.name, "tag", begin.n1, sub_tag);
				n.add(index, tag);
				return true;
			}
		} while(true);
		return false;
	}

	private boolean find_p(ArrayList<XMLNode> n) {
		int index = -1;
		do {
			index = indexOf(n, XMLNode.getType(XMLTocenType.PBEGIN_TAG), index+1);
			if(index == -1) break;
			if(n.get(index+1).isType(XMLTocenType.PEND_TAG)) {
				n.remove(index);
				n.remove(index);
				return true;
			}
			if(n.get(index+1).isType("atr") && n.get(index+2).isType(XMLTocenType.PEND_TAG)) {
				n.remove(index);
				n.remove(index);
				n.remove(index);
				return true;
			}

		} while(true);
		return false;
	}

	private boolean find_tags(List<XMLNode> n) {
		int index = -1;
		do {
			index = indexOf(n, XMLNode.getType("data"), index+1);
			if((index == -1) || (index == n.size()-1)) break;
			final var n1 = n.get(index+0);
			final var n2 = n.get(index+1);
			if(n1.isType("datas") && n2.isType("datas")) continue;
			if(n2.isType("data")) {
				n.remove(index);
				n.remove(index);
				final var tag = new XMLNode("", "datas", n1, n2);
				n.add(index, tag);
				return true;
			}
		} while(true);
		return false;
	}

	private XMLElement run(XMLNode node) {
		if(node.isType("tag")) {
			final var atr = run_atr(node.n1);
			final var chl = run_tag(node.n2);
			final var con = run_con(node.n2);
			return new XMLElement(chl, atr, node.name, con);
		}
		throw new IllegalArgumentException(""+node);
	}

	private Map<String, String> run_atr(XMLNode n) {
		if(n == null)
			return new HashMap<>();
		if(n.isType("atrs")) {
			final var res = new HashMap<String, String>();
			final var a = run_atr(n.n1);
			final var b = run_atr(n.n2);
			res.putAll(a);
			res.putAll(b);
			return res;
		}
		final var str = n.name.split("=");
		return Map.of(str[0], str[1].substring(1, str[1].length() - 1));
	}

	private String run_con(XMLNode n) {
		if(n == null)
			return "";
		if(n.isType("datas"))
			return run_con(n.n1) + run_con(n.n2);
		if(n.isType("text"))
			return n.name;
		return "";
	}

	private Collection<XMLElement> run_tag(XMLNode n) {
		if(n == null)
			return new ArrayList<>();
		if(n.isType("datas")) {
			final var res = new ArrayList<XMLElement>();
			final var a = run_tag(n.n1);
			final var b = run_tag(n.n2);
			res.addAll(a);
			res.addAll(b);
			return res;
		}
		if(n.isType("text"))
			return List.of();
		return List.of(run(n));
	}

	private static record XMLNode(String name, String type, XMLNode n1, XMLNode n2, XMLNode n3) {

		private static final Map<String, String> superType = new HashMap<>();
		private static final String typeSplit = "&";
		static {
			superType.put("atrs", "atr");
			superType.put("datas", "data");
			superType.put("tag", "data");
			superType.put("text", "data");
		}

		@Override
		public String toString() {
			final var str = new ArrayList<String>();
			if(n1 != null) str.add(n1.toString());
			if(n2 != null) str.add(n2.toString());
			if(n3 != null) str.add(n3.toString());
			if(name.isBlank()) {
				if(str.isEmpty())
					return type;
				return type+"{"+IteratorUtil.reduce(str, (a, b) -> a+", "+b)+"}";
			}else {
				if(str.isEmpty())
					return type+"("+name+")";
				return type+"("+name+"){"+IteratorUtil.reduce(str, (a, b) -> a+", "+b)+"}";
			}
		}

		public XMLNode(String name, String type) {
			this(name, type, null, null, null);
		}
		public XMLNode(String name, String type, XMLNode n1) {
			this(name, type, n1, null, null);
		}
		public XMLNode(String name, String type, XMLNode n1, XMLNode n2) {
			this(name, type, n1, n2, null);
		}
		public XMLNode(String name, String type, XMLNode n1, XMLNode n2, XMLNode n3) {
			this.name = name;
			this.type = getType(type.toLowerCase());
			this.n1 = n1;
			this.n2 = n2;
			this.n3 = n3;
		}
		public XMLNode(String name, XMLTocenType type) {
			this(name, type.name(), null, null, null);
		}
		public XMLNode(String name, XMLTocenType type, XMLNode n1) {
			this(name, type.name(), n1, null, null);
		}
		public XMLNode(String name, XMLTocenType type, XMLNode n1, XMLNode n2) {
			this(name, type.name(), n1, n2, null);
		}

		public boolean isType(String type) {
			for(var t : type().split(typeSplit)) if(t.equals(type)) return true;
			return false;
		}

		private static String getType(String type) {
			if(superType.containsKey(type)) {
				final var st = superType.get(type);
				return type+typeSplit+getType(st);
			}
			return type;
		}

		public static XMLNode create(XMLTocen tocen) {
			return new XMLNode(tocen.text(), tocen.type().name());
		}
		public static String getType(XMLTocenType type) {
			return type.name().toLowerCase();
		}
		public boolean isType(XMLTocenType type) {
			return isType(getType(type));
		}
	}

}
