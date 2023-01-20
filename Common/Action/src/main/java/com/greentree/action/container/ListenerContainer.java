package com.greentree.action.container;

import java.io.Serializable;

import com.greentree.action.ListenerCloser;
import com.greentree.common.util.iterator.IteratorUtil;
import com.greentree.common.util.iterator.SizedIterable;

public interface ListenerContainer<L> extends SizedIterable<L>, Serializable {

	ListenerCloser add(L listener);
	
	default int size() {
		return IteratorUtil.size(iterator());
	}
	
	void clear();
	
}
