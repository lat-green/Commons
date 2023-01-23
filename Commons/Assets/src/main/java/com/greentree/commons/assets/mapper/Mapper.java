package com.greentree.commons.assets.mapper;

import com.greentree.commons.assets.source.Source;
import com.greentree.commons.assets.store.Store;

public interface Mapper<IN, OUT> extends Store<IN>, Source<OUT> {
	
	
	
}
