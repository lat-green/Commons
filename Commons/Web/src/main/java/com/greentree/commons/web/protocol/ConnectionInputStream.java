package com.greentree.commons.web.protocol;

import java.io.IOException;
import java.io.InputStream;

public class ConnectionInputStream extends InputStream {

	private final StreamableConnection connection;

	public ConnectionInputStream(StreamableConnection connection) {
		this.connection = connection;
	}

	@Override
	public int read(byte[] buf, int off, int cnt) throws IOException {
		return connection.read(buf, off, cnt);
	}
	
	@Override
	public int read() throws IOException {
		if(connection.isClosed()) return -1;
		final var arr = new byte[1];
		connection.read(arr, 0, arr.length);
		return Byte.toUnsignedInt(arr[0]);
	}

}
