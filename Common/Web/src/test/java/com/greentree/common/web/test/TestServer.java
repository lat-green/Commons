package com.greentree.common.web.test;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

public class TestServer {

	public static void main(String[] args) throws SocketException {
		findHostInLan();
	}

	//Realtek 8821CE Wireless LAN 802.11ac PCI-E NIC
	private static void findHostInLan() throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if(networkInterface.isLoopback() || !networkInterface.isUp()) continue;
			System.out.println(networkInterface.getDisplayName());
			for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				final var broadcast = interfaceAddress.getBroadcast();
				if(broadcast != null) log("b", broadcast);
				final var  i = interfaceAddress.getAddress();
				if(i != null)log("a", i);
				System.out.println();
			}
		}
	}

	private static void log(CharSequence c, InetAddress b) {
		System.out.println(c+ " " + b.getHostAddress() + " " + b.getHostName() + " " + b.getCanonicalHostName() + " " + Arrays.toString(b.getAddress()));
	}



}
