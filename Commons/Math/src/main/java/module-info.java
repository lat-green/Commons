
open module common.math {

	requires transitive common.action;

	requires transitive org.joml;

	exports com.greentree.common.math;
	exports com.greentree.common.math.vector;
	exports com.greentree.common.math.vector.link;
	exports com.greentree.common.math.geometry;
	exports com.greentree.common.math.analysis;

}