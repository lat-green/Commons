package com.greentree.common.util.factory;

import com.greentree.common.util.classes.info.TypeInfo;
import com.greentree.common.util.classes.info.TypeUtil;
import com.greentree.common.util.factory.ObjectFactories.InstanceConstructor;

public interface ObjectFactory<T> {
	
	default TypeInfo<T> getType() {
		return TypeUtil.getFirstAtgument(getClass(), ObjectFactory.class);
	}
	
	InstanceConstructor<T> newInstance(InstanceProperties properties);
	
}
