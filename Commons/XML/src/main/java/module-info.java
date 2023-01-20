open module common.xml {

	requires transitive java.xml;
	requires transitive common.util;

	exports com.greentree.common.xml;
	exports com.greentree.common.xml.parser;
	
}