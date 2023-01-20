package com.greentree.common.web.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

public class TestClient implements Runnable {

	public static TestClient getInstance() {
		return DiscoveryThreadHolder.INSTANCE;
	}

	private static void log(InetAddress b) {
		System.out.println(b.getHostAddress() + " " + b.getHostName() + " " + b.getCanonicalHostName() + " " + b.getCanonicalHostName() + " " + Arrays.toString(b.getAddress()));
	}

	public static void main(String[] args) {
		Thread discoveryThread = new Thread(TestClient.getInstance());
		discoveryThread.start();
	}

	@Override
	public void run() {
		// Find the server using UDP broadcast
		try {
			//Open a random port to send the package
			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);
			
			byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

			// Broadcast the message over all the network interfaces
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback() || !networkInterface.isUp()) continue; // Don't want to broadcast to the loopback interface

				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) continue;

					// Send the broadcast package!
					try {
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
						socket.send(sendPacket);
					} catch (Exception e) {
					}

					System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				}
			}

			System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

			//Wait for a response
			byte[] recvBuf = new byte[15000];
			DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
			socket.receive(receivePacket);

			//We have a response
			System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

			//Check if the message is correct
			String message = new String(receivePacket.getData()).trim();
			if ("DISCOVER_FUIFSERVER_RESPONSE".equals(message))
				System.out.println(receivePacket.getAddress().getHostName());

			//Close the port!
			socket.close();
		} catch (IOException ex) {
			//		  Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static class DiscoveryThreadHolder {

		private static final TestClient INSTANCE = new TestClient();
	}
}
