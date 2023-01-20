package com.greentree.common.web.protocol.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import com.greentree.common.util.concurent.MultiTask;
import com.greentree.common.util.function.CheckedRunnable;
import com.greentree.common.web.protocol.Connection;


public class UDPConnection implements Connection {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UDPTransportProtocolConnection [socket=");
		builder.append(socket);
		builder.append(", address=");
		builder.append(address);
		builder.append(", port=");
		builder.append(port);
		builder.append("]");
		return builder.toString();
	}

	private final DatagramSocket socket;
	private final InetAddress address;
	private final int port;

	public UDPConnection(InetAddress address, int port) throws IOException, TimeoutException {
		socket = new DatagramSocket();
		this.address = address;
		this.port = port;
		foundServer();
	}

	private static final int TIMEOUT_FOUND_SERVER = 1000;
	private static final int TIMEOUT_STEP = 33;
	
	private void foundServer() throws IOException, TimeoutException {
		final var res = new boolean[1];
		final var empty = new byte[0];
		task(() -> {
			read(empty, 0, empty.length);
			synchronized(res) {
				res[0] = true;
			}
		});
		final var num = new byte[] { 1 };
		var next = getTime();
		var end = next + TIMEOUT_FOUND_SERVER;
		while(true) {
			synchronized(res) {
				if(res[0]) {
					num[0] = -1;
					write(num, 0, num.length);
					return;
				}
			}
			final var time = getTime();
			if(next < time) {
				if(time > end) throw new TimeoutException();
				write(num, 0, num.length);
				next = time + TIMEOUT_STEP;
			}
		}
	}

	private static Future<?> task(CheckedRunnable runnable) {
		return MultiTask.task(runnable.toNonCheked());
	}
	
	private static long getTime() {
		return System.currentTimeMillis();
	}
	
	public UDPConnection(int port) throws IOException {
		socket = new DatagramSocket(port);
		final var client = getClient();
		this.address = client.getAddress();
		this.port = client.getPort();
	}

	private InetSocketAddress getClient() throws IOException {
		final var empty = new byte[1];
		final var packet1 = new DatagramPacket(empty, 0, empty.length);
		socket.receive(packet1);
		final var address = packet1.getAddress();
		final var port = packet1.getPort();
		final var packet = new DatagramPacket(empty, 0, empty.length, address, port);
		socket.send(packet);
		
		final var packet2 = new DatagramPacket(empty, 0, empty.length);
		while(empty[0] != -1) {
			socket.receive(packet2);
		}
		
		return new InetSocketAddress(address, port);
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
	public int read(byte[] b, int off, int cnt) throws IOException {
		final var packet = new DatagramPacket(b, off, cnt);
		socket.receive(packet);
		if(!packet.getAddress().equals(address) || (packet.getPort() != port)) throw new IllegalArgumentException();
		return cnt - off;
	}

	@Override
	public void write(byte[] b, int off, int cnt) throws IOException {
		final var packet = new DatagramPacket(b, off, cnt, address, port);
		socket.send(packet);
	}

}
