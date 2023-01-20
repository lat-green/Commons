package com.greentree.common.util.collection;

import java.io.Serializable;

public interface MapID extends Serializable {

	void free(int i);
	int get();

}
