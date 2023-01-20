package com.greentree.commons.assets.serializator;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;
import com.greentree.commons.util.classes.info.TypeUtil;

public abstract class TypedAssetSerializator<T> implements AssetSerializator<T> {
	
	public final TypeInfo<T> TYPE;
	
	protected TypedAssetSerializator() {
		TYPE = TypeUtil.getFirstAtgument(TypeInfoBuilder.getTypeInfo(getClass()),
				TypedAssetSerializator.class);
	}
	
	public TypedAssetSerializator(TypeInfo<T> type) {
		TYPE = type;
	}
	
	public TypedAssetSerializator(Class<T> cls) {
		this(TypeInfoBuilder.getTypeInfo(cls));
	}
	
	@Override
	public TypeInfo<T> getType() {
		return TYPE;
	}
	
}
