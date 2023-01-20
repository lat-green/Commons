package com.greentree.commons.assets.location;

import java.util.stream.Stream;

public interface NamedAssetLocation extends AssetLocation {
	
	Stream<String> names();
	
}
