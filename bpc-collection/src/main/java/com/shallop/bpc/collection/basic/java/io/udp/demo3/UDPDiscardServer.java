package com.shallop.bpc.collection.basic.java.io.udp.demo3;

/**
 * @author StickChen
 * @date 2017/1/14
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPDiscardServer {

	private final static int DEFAULT_PORT = 9;
	private final static int MAX_PACKET_SIZE = 65507;

	public static void main(String[] args) {

		int port = DEFAULT_PORT;
		byte[] buffer = new byte[MAX_PACKET_SIZE];
		try {
			DatagramSocket server = new DatagramSocket(port);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			while (true) {
				server.receive(packet);
				String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), "UTF-8");
				System.out.println(packet.getAddress() + " at port:" + packet.getPort() + " says:\n" + s);
				// 设置以后需要接受的长度
				packet.setLength(buffer.length);
			}

		} catch (SocketException e) {

		} catch (IOException e) {

		}
	}
}
