package com.shallop.bpc.collection.basic.java.io.udp.demo1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author StickChen
 * @date 2017/1/14
 */
public class UDPClient {
	// 定义数据报的目的地
	public static final int DEST_PORT = 30000;
	public static final String DEST_IP = "127.0.0.1";
	// 定义每个数据报的最大的大小为4KB
	public static final int DATA_LEN = 4096;
	// 定义接受网络数据的字节数组
	byte[] inBuff = new byte[DATA_LEN];
	// 已指定字节数组创建准备接受数据的DatagramPacket对象
	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	// 定义发送的DatagramPacket对象
	private DatagramPacket outPacket;
	// 定义一个字符串数组，服务器发送该数组的元素
	String[] book = new String[] { "I", "am", "Stu" };

	public void init() {
		try {
			// 创建一个客户端DatagramSocket使用随机端口
			DatagramSocket socket = new DatagramSocket();
			outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(DEST_IP), DEST_PORT);
			// 创建键盘输入流
			Scanner scan = new Scanner(System.in);
			// 不断读取键盘输入
			while (scan.hasNextLine()) {
				// 将键盘输入的一行字符串转换字节数组
				byte[] buff = scan.nextLine().getBytes();
				// 设置发送用到的DatagramPacket中的字节数据
				outPacket.setData(buff);
				// 发送数据报
				socket.send(outPacket);
				// 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中
				socket.receive(inPacket);
				System.out.println(new String(inBuff, 0, inPacket.getLength()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new UDPClient().init();
	}
}
