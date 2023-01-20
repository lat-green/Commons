
open module commons.math {
	
	requires transitive commons.action;
	
	requires transitive org.joml;
	
	exports com.greentree.commons.math;
	exports com.greentree.commons.math.vector;
	exports com.greentree.commons.math.vector.link;
	exports com.greentree.commons.math.geometry;
	exports com.greentree.commons.math.analysis;
	
}
