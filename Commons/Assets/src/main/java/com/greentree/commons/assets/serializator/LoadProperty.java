package com.greentree.commons.assets.serializator;

import java.util.ArrayList;

public enum LoadProperty {

	NULLABLE      (false),
	LOAD_CONST    (true),
	LOAD_ASYNC    (true, LOAD_CONST),
	NOT_SERIALIZABLE(true),
	;
	
	private int mask;

	public int mask() {
		return mask;
	}
	
	static {
		final var vs = values();
		for(int i = 0, mask = 1; i < vs.length; i++, mask*=2)
			vs[i].mask = mask;
	}
	
	public final boolean useInSubAsset;
	
	LoadProperty(boolean useInSubAsset, LoadProperty...conflict) {
		this.useInSubAsset = useInSubAsset;
	}

	public static int add(int properties, LoadProperty property) {
		return properties | property.mask;
	}

	public static int getMask(LoadProperty...properties) {
		if(properties==null)
			return 0;
		int mask = 0;
		for(var p : properties)
			mask |= p.mask;
		return mask;
	}

	public static int getSubAssetMask(int properties) {
		for(var p : values())
			if(!p.useInSubAsset)
				properties = remove(properties, p);
		return properties;
	}

	public static boolean has(int properties, LoadProperty property) {
		final var result = properties & property.mask;
		return result > 0;
	}

	public static int remove(int properties, LoadProperty property) {
		return properties & ~property.mask;
	}

	@Override
	public String toString() {
		return super.toString() + "("+mask+")";
	}

	public static LoadProperty[] getArray(int properties) {
		final var list = new ArrayList<>();
		for(var p : LoadProperty.values())
			if(has(properties, p))
				list.add(p);
		return list.toArray(new LoadProperty[list.size()]);
	}

}
