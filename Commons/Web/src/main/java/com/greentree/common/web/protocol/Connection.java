package com.greentree.common.web.protocol;

import java.io.IOException;
import java.net.InetAddress;

public interface Connection extends AutoCloseable {
	
	@Override
	void close() throws IOException;
	
	int getLocalPort();

	int read(byte[] buf, int off, int cnt) throws IOException;
	void write(byte[] buf, int off, int cnt) throws IOException;

	boolean isClosed() throws IOException;

	InetAddress getLocalAddress();
	
}
