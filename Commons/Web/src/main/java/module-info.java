
open module common.web {

	requires transitive common.util;
	requires transitive common.action;

	exports com.greentree.common.web.protocol;
	exports com.greentree.common.web.protocol.tcp;
	exports com.greentree.common.web.protocol.udp;
	exports com.greentree.common.web.protocol.netchannel;
	exports com.greentree.common.web.room;
	

}