
open module commons.geometry {
	
	requires transitive commons.math;
	
	exports com.greentree.commons.geometry.geom2d;
	exports com.greentree.commons.geometry.geom2d.shape;
	exports com.greentree.commons.geometry.geom2d.collision;
	exports com.greentree.commons.geometry.geom2d.collision.strategy;
	exports com.greentree.commons.geometry.geom2d.collision.strategy.dual;
	exports com.greentree.commons.geometry.geom2d.collision.strategy.world;
	exports com.greentree.commons.geometry.geom2d.util;
	
	exports com.greentree.commons.geometry.geom3d;
	exports com.greentree.commons.geometry.geom3d.mesh;
	exports com.greentree.commons.geometry.geom3d.shape;
	exports com.greentree.commons.geometry.geom3d.collision;
	exports com.greentree.commons.geometry.geom3d.collision.strategy;
	exports com.greentree.commons.geometry.geom3d.face;
	
	exports com.greentree.commons.geometry.math;
	
}
