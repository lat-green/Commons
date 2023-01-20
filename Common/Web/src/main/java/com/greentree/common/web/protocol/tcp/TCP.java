package com.greentree.common.web.protocol.tcp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.greentree.common.web.protocol.TransportProtocol;

public class TCP implements TransportProtocol {

	public static final TCP INSTANCE = new TCP();
	
	private TCP() {
	}
	
	@Override
	public int getVacantPort() throws SocketException {
		try(final var socket = new DatagramSocket();) {
			return socket.getLocalPort();
		}
	}
	
	@Override
	public TCPConnection open(int port) throws IOException {
		return new TCPConnection(port);
	}
	
	@Override
	public TCPConnection open(InetAddress address, int port) throws IOException {
		return new TCPConnection(address, port);
	}

}
