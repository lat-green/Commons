package com.greentree.commons.web.protocol.netchannel;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeoutException;

import com.greentree.commons.web._private.ByteQueue;
import com.greentree.commons.web._private.ByteUtils;
import com.greentree.commons.web.protocol.ProxyConnection;
import com.greentree.commons.web.protocol.StreamableConnection;
import com.greentree.commons.web.protocol.udp.UDP;
import com.greentree.commons.web.protocol.udp.UDPConnection;

public final class NetChannel extends ProxyConnection<UDPConnection> implements StreamableConnection {

	private static final int MESSAGE_HEADER_SIZE = Integer.BYTES * 2;
	private static final int MESSAGE_SIZE = 1024;
	public static final int MESSAGE_DATA_SIZE = MESSAGE_SIZE - MESSAGE_HEADER_SIZE;
	private static final int BUFFER_SIZE = 50 * MESSAGE_SIZE;

	private boolean readySend = true;
	private final ByteQueue write_buf = new ByteQueue(BUFFER_SIZE);
	private final byte[] current_buf = new byte[MESSAGE_DATA_SIZE];

	private final ByteQueue read_buf = new ByteQueue(BUFFER_SIZE);
	private int sequence = -1, sequenceASK = -1;

	public int getReadyRead() {
		return read_buf.size();
	}
	
	public NetChannel(InetAddress address, int port) throws IOException, TimeoutException {
		this(UDP.INSTANCE.open(address, port));
	}

	public NetChannel(int port) throws IOException {
		this(UDP.INSTANCE.open(port));
	}

	private NetChannel(UDPConnection connection) {
		super(connection);
	}
	
	@Override
	public int read(byte[] buf, int off, int cnt) throws IOException {
		while(read_buf.size() < cnt) readNextPacket();
		read_buf.pop(buf, off, cnt);
		return cnt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NetChannel [");
		builder.append(origin);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void write(byte[] buf, int off, int cnt) throws IOException {
		write_buf.push(buf, off, cnt);
		tryWriteCurrentPacket();
	}

	private void readNextPacket() throws IOException {
		final var arr = new byte[MESSAGE_SIZE];
		origin.read(arr, 0, MESSAGE_SIZE);
		handleHeader(arr);
		read_buf.push(arr, MESSAGE_HEADER_SIZE, MESSAGE_DATA_SIZE);
	}

	private void tryWriteCurrentPacket() throws IOException {
		if(write_buf.size() >= MESSAGE_DATA_SIZE)
    		if(readySend) {
    			readySend = false;
    			write_buf.pop(current_buf);
    			sequence++;
    			writeCurrentPacket();
    		}else readNextPacket();
	}

	private void writeCurrentPacket() throws IOException {
		final var arr = new byte[MESSAGE_SIZE];
		fillHeader(arr);
		for(int i = 0; i < MESSAGE_DATA_SIZE; i++)
			arr[i + MESSAGE_HEADER_SIZE] = current_buf[i];
		origin.write(arr, 0, MESSAGE_SIZE);
	}

	protected void fillHeader(byte[] arr) {
		ByteUtils.intToBytes(sequence   , arr, 0 * Integer.BYTES);
		ByteUtils.intToBytes(sequenceASK, arr, 1 * Integer.BYTES);
	}

	protected void handleHeader(byte[] arr) throws IOException {
		final var s    = ByteUtils.bytesToInteger(arr, 0 * Integer.BYTES);
		final var sASK = ByteUtils.bytesToInteger(arr, 1 * Integer.BYTES);
		sequenceASK = s;
		if(sASK == sequence)
			readySend = true;
		else
			writeCurrentPacket();
	}


}
