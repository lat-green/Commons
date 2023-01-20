package com.greentree.common.web.protocol;

import java.io.IOException;
import java.net.InetAddress;

public abstract class ProxyConnection<TPC extends Connection> implements Connection {
	
	protected final TPC origin;

	public ProxyConnection(TPC origine) {
		this.origin = origine;
	}

	@Override
	public void close() throws IOException {
		origin.close();
	}

	@Override
	public int getLocalPort() {
		return origin.getLocalPort();
	}

	@Override
	public boolean isClosed() throws IOException {
		return origin.isClosed();
	}

	@Override
	public InetAddress getLocalAddress() {
		return origin.getLocalAddress();
	}


}
