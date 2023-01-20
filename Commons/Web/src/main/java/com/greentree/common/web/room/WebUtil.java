package com.greentree.common.web.room;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebUtil {

	public static Socket waitSocket(final int port) throws IOException {
		try(ServerSocket server = new ServerSocket(port);) {
			return server.accept();
		}
	}

}
