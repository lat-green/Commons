package com.greentree.common.web.test;

import java.net.InetAddress;

import com.greentree.common.util.function.CheckedRunnable;
import com.greentree.common.web.protocol.netchannel.NetChannel;

public class NetChannelTest {

	private static Thread server, client;

	private final static int SERVER_PORT = 8888;


	public static void main(String[] args) throws InterruptedException {
		server = new Thread(run(NetChannelTest::server), "SERVER");
		client = new Thread(run(NetChannelTest::client), "CLIENT");

		client.start();
		server.start();

//		new Thread(run(() ->{
//			Thread.sleep(10000);
//			System.exit(0);
//		}), "EXIT").start();

		server.join();
		client.join();


	}

	private static final int COUNT = 1;
	
	private static void client() throws Exception {
		try(final var channel = new NetChannel(InetAddress.getByName("localhost"), SERVER_PORT);) {

			final var arr = new byte[1];

			for(int i = 0; i < COUNT*NetChannel.MESSAGE_DATA_SIZE; i++) {
				channel.read(arr, 0, arr.length);
				arr[0] += i;
				channel.write(arr, 0, arr.length);
			}
		}
	}

	private static Runnable run(CheckedRunnable runnable) {
		return runnable.toNonCheked();
	}

	private static void server() throws Exception {
		try(final var channel = new NetChannel(SERVER_PORT);) {

			final var arr = new byte[1];
			for(int i = 0; i < COUNT*NetChannel.MESSAGE_DATA_SIZE; i++) {
				channel.write(arr, 0, arr.length);
			}
			for(int i = 0; i < COUNT*NetChannel.MESSAGE_DATA_SIZE; i++) {
				channel.read(arr, 0, arr.length);
				System.out.println(arr[0]);
			}

		}
	}

}
