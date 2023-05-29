package com.greentree.commons.injector.factory;

import com.greentree.commons.injector.factory.ObjectFactories.InstanceConstructor;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.reflection.info.TypeUtil;

public interface ObjectFactory<T> {
	
	default TypeInfo<T> getType() {
		return TypeUtil.getFirstAtgument(getClass(), ObjectFactory.class);
	}
	
	InstanceConstructor<T> newInstance(InstanceProperties properties);
	
}
