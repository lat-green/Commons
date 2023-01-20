package com.greentree.action.observer;

import java.io.Serializable;

public interface IntObserver extends Serializable {

	void event(int i);
	
}
