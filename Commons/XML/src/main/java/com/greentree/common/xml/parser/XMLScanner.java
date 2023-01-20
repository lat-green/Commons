package com.greentree.common.xml.parser;

import static com.greentree.common.util.iterator.IteratorUtil.filter;
import static com.greentree.common.util.iterator.IteratorUtil.iterable;
import static com.greentree.common.util.iterator.IteratorUtil.map;
import static com.greentree.common.util.iterator.IteratorUtil.min;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.greentree.common.util.cortege.Pair;

public class XMLScanner implements Iterator<XMLTocen> {

	private final String code;
	private int position;

	public XMLScanner(String code) {
		int index = -1;
		do {
			index = code.indexOf("<!--", index + 1);
			if(index == -1) break;
			final var e = code.indexOf("-->", index);
			code = code.substring(0, index) + code.substring(e + 3);
			index = e;
		}while(true);
		this.code = code.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ').replace("  ", " ");
	}

	@Override
	public boolean hasNext() {
		return position < code.length();
	}

	@Override
	public XMLTocen next() {
		final var p1 = iterable(XMLTocenType.values());
		final var p2 = map(p1, tt-> {
			final var m = tt.find(position, code);
			if(m == null) return null;
			return new Pair<>(tt, m);
		});
		final var p3 = filter(p2, p->p != null);
		final var pr = min(p3, p->p.seconde.end());

		if(pr != null) {
			final var m = pr.seconde;
			position = m.end();
			final var t = new XMLTocen(m.substring(code), pr.first);
			return t;
		}
		throw new NoSuchElementException("position=" + position + " length=" + code.length() + " " + code.substring(position));
	}

}
