package com.greentree.commons.util.factory;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeUtil;
import com.greentree.commons.util.factory.ObjectFactories.InstanceConstructor;

public interface ObjectFactory<T> {
	
	default TypeInfo<T> getType() {
		return TypeUtil.getFirstAtgument(getClass(), ObjectFactory.class);
	}
	
	InstanceConstructor<T> newInstance(InstanceProperties properties);
	
}
