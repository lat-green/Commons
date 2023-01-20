package com.greentree.commons.action.observer;

import java.io.Serializable;

public interface IntObserver extends Serializable {

	void event(int i);
	
}
