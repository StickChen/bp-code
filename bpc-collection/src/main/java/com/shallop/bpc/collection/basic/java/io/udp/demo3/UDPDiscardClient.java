package com.shallop.bpc.collection.basic.java.io.udp.demo3;

/**
 * @author StickChen
 * @date 2017/1/14
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//Discard以端口9,该服务器主要就是丢弃所有的数据
public class UDPDiscardClient {
	// discard 的端口为9
	private static final int DEFAULT_PORT = 9;

	public static void main(String[] args) {
		String hostname = "localhost";
		int port = DEFAULT_PORT;
		try {
			InetAddress server = InetAddress.getByName(hostname);
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			// 创建UDP客户端
			DatagramSocket client = new DatagramSocket();
			while (true) {
				String inline = userIn.readLine();
				if (inline.indexOf('.') != -1)
					break;
				byte[] data = inline.getBytes("UTF-8");

				// 数据报
				DatagramPacket thepacket = new DatagramPacket(data, data.length, server, port);
				client.send(thepacket);
			}
			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
