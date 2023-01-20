package com.greentree.commons.web.protocol;

import java.io.IOException;
import java.io.OutputStream;


public class ConnectionOutputStream extends OutputStream {

	private final StreamableConnection connection;
	
	public ConnectionOutputStream(StreamableConnection connection) {
		this.connection = connection;
	}

	@Override
	public void write(byte[] buf, int off, int cnt) throws IOException {
		connection.write(buf, off, cnt);
	}

	@Override
	public void write(int b) throws IOException {
		if(connection.isClosed()) throw new IOException(connection + " is closed");
		final var arr = new byte[] { (byte) b };
		connection.write(arr, 0, arr.length);
	}

}
