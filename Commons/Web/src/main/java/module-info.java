
open module commons.web {
	
	requires transitive commons.util;
	requires transitive commons.action;
	
	exports com.greentree.commons.web.protocol;
	exports com.greentree.commons.web.protocol.tcp;
	exports com.greentree.commons.web.protocol.udp;
	exports com.greentree.commons.web.protocol.netchannel;
	exports com.greentree.commons.web.room;
	
	
}
