open module common.image {

	requires transitive common.util;

	requires transitive java.desktop;

	exports com.greentree.common.image.loader;
	exports com.greentree.common.image.writer;
	exports com.greentree.common.image.image;
	exports com.greentree.common.image;

}
