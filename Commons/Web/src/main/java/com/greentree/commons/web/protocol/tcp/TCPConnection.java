package com.greentree.commons.web.protocol.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.greentree.commons.web.protocol.StreamableConnection;


public class TCPConnection implements StreamableConnection {

	private final Socket socket;

	public TCPConnection(InetAddress address, int port) throws IOException {
		socket = new Socket(address, port);
	}

	public TCPConnection(int port) throws IOException {
		try(final var server = new ServerSocket(port)) {
			socket = server.accept();
		}
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}
	@Override
	public InetAddress getLocalAddress() {
		return socket.getLocalAddress();
	}

	@Override
	public int getLocalPort() {
		return socket.getLocalPort();
	}

	@Override
	public boolean isClosed() throws IOException {
		return socket.isClosed();
	}

	@Override
	public int read(byte[] buf, int off, int len) throws IOException {
		return socket.getInputStream().read(buf, off, len);
	}

	@Override
	public void write(byte[] buf, int off, int len) throws IOException {
		socket.getOutputStream().write(buf, off, len);
	}


}
