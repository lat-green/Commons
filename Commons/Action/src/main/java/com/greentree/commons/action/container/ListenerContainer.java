package com.greentree.commons.action.container;

import java.io.Serializable;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.commons.util.iterator.SizedIterable;

public interface ListenerContainer<L> extends SizedIterable<L>, Serializable {

	ListenerCloser add(L listener);
	
	default int size() {
		return IteratorUtil.size(iterator());
	}
	
	void clear();
	
}
