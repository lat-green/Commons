package com.greentree.commons.web.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

public interface TransportProtocol {

	int getVacantPort() throws SocketException;

	Connection open(int port) throws IOException;
	
	Connection open(InetAddress address, int port) throws IOException, TimeoutException;

}
