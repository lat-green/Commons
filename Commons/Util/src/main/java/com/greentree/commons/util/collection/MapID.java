package com.greentree.commons.util.collection;

import java.io.Serializable;

public interface MapID extends Serializable {

	void free(int i);
	int get();

}
