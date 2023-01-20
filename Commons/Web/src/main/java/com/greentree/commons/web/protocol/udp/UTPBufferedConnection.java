package com.greentree.commons.web.protocol.udp;

import com.greentree.commons.web.protocol.BufferedConnection;

@Deprecated
public class UTPBufferedConnection extends BufferedConnection<UDPConnection> {


	public UTPBufferedConnection(UDPConnection origine, int heder_size, int data_size) {
		super(origine, heder_size, data_size);
	}
	
	public UTPBufferedConnection(UDPConnection origine, int heder_size) {
		super(origine, heder_size);
	}

	public UTPBufferedConnection(UDPConnection origine) {
		super(origine);
	}

}
