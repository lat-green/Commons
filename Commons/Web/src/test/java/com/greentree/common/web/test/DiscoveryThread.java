package com.greentree.common.web.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DiscoveryThread implements Runnable {
	private static final DiscoveryThread INSTANCE = new DiscoveryThread();

	DatagramSocket socket;

	public static DiscoveryThread getInstance() {
		return DiscoveryThread.INSTANCE;
	}

	public static void main(String[] args) {
		Thread discoveryThread = new Thread(INSTANCE);
		discoveryThread.start();
	}
	
	@Override
	public void run() {
		try {
			//Keep a socket open to listen to all the UDP trafic that is destined for this port
			socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			System.out.println(socket.getLocalSocketAddress());
			
			while (!socket.isClosed()) {
				System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

				//Receive a packet
				byte[] recvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);

				//Packet received
				System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
				System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

				//See if the packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				if ("DISCOVER_FUIFSERVER_REQUEST".equals(message)) {
					byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

					//Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
					socket.send(sendPacket);

					System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			//		      Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
