package com.greentree.commons.action.container;

import java.io.Serializable;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.util.iterator.IteratorUtil;

public interface TypedListenerContainer<T, L> extends Serializable {
	
	ListenerCloser add(L listener);
	
	ListenerCloser add(T type, L listener);
	
	Iterable<? extends L> listeners(T type);
	
	default int size(T type) {
		return IteratorUtil.size(listeners(type));
	}
}
