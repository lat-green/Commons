package com.greentree.commons.web.protocol.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import com.greentree.commons.web.protocol.TransportProtocol;

public class UDP implements TransportProtocol {

	public static final UDP INSTANCE = new UDP();
	
	private UDP() {
	}
	
	@Override
	public int getVacantPort() throws SocketException {
		try(final var socket = new DatagramSocket();) {
			return socket.getLocalPort();
		}
	}
	
	@Override
	public UDPConnection open(int port) throws IOException {
		return new UDPConnection(port);
	}
	
	@Override
	public UDPConnection open(InetAddress address, int port) throws IOException, TimeoutException {
		return new UDPConnection(address, port);
	}

}
