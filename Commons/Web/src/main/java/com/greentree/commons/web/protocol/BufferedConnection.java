package com.greentree.commons.web.protocol;

import java.io.IOException;

import com.greentree.commons.web._private.ByteQueue;

@Deprecated
public class BufferedConnection<TPC extends Connection> extends ProxyConnection<TPC> {

	public static final int DEFAULT_HEDER_SIZE = 0;
	public static final int DEFAULT_DATA_SIZE = 1024;
	protected final int hederSize, dataSize;

	public int getBufferSize() {
		return hederSize + dataSize;
	}
	public int getDataSize() {
		return dataSize;
	}
	public int getHederSize() {
		return hederSize;
	}
	
	private final ByteQueue write_buf;
	private final ByteQueue read_buf;
	

	public BufferedConnection(TPC origine) {
		this(origine, DEFAULT_HEDER_SIZE, DEFAULT_DATA_SIZE);
	}
	public BufferedConnection(TPC origine, int heder_size) {
		this(origine, heder_size, DEFAULT_DATA_SIZE);
	}
	public BufferedConnection(TPC origine, int heder_size, int data_size) {
		super(origine);
		this.dataSize = data_size;
		this.hederSize = heder_size;
		write_buf = new ByteQueue(dataSize);
		read_buf = new ByteQueue(dataSize);
	}

	@Override
	public int read(byte[] buf, int off, final int cnt) throws IOException {
		var len = cnt;
		while(len > 0) if(len > read_buf.size()) {
			if(read_buf.isEmpty()) {
				final var arr = new byte[hederSize + dataSize];
				origin.read(arr, 0, arr.length);
				handleHeder(arr);
				read_buf.push(arr, hederSize, dataSize);
			}
			final var s = read_buf.size();
			read_buf.pop(buf, off, s);
			off += s;
			len -= s;
		}else read_buf.pop(buf, off, cnt);
		return cnt;
	}

	protected void fillHeder(byte[] arr) {
	}
	
	protected void handleHeder(byte[] arr) {
	}
	
	@Override
	public void write(byte[] buf, int off, int len) throws IOException {
		while(len > 0) if(len > write_buf.free()) {
			final var s = write_buf.fill(buf, off);
			len -= s;
			off += s;
			var arr = new byte[hederSize + dataSize];
			write_buf.pop(arr, hederSize, dataSize);
			fillHeder(arr);
			origin.write(arr, 0, arr.length);
		}else write_buf.push(buf, off, len);
	}

}
