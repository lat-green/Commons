open module commons.assets {
	
	requires transitive commons.data;
	requires transitive commons.action;
	
	exports com.greentree.commons.assets.value;
	exports com.greentree.commons.assets.value.map;
	exports com.greentree.commons.assets.value.merge;
	exports com.greentree.commons.assets.source.function;
	
	exports com.greentree.commons.assets.key;
	exports com.greentree.commons.assets.location;
	exports com.greentree.commons.assets.serializator;
	exports com.greentree.commons.assets.serializator.context;
	exports com.greentree.commons.assets.serializator.manager;
	exports com.greentree.commons.assets.serializator.request;
	
}
