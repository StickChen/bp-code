package com.shallop.bpc.collection.basic.java.io.udp.demo1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author StickChen
 * @date 2017/1/14
 */
public class UDPServer {
	public static final int PORT = 30000;
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
			// 创建datagramsocket对象
			DatagramSocket socket = new DatagramSocket(PORT);
			{
				// 采用循环接受数据
				for (int i = 0; i < 1000; i++) {
					// 读取inPacket的数据
					socket.receive(inPacket);
					// 判断getData()和inbuf是否为同一个数组
					System.out.println(inPacket.getData() == inBuff);
					System.out.println(socket.getSoTimeout());
					// 将接受后的内容转化为字符串进行输出
					System.out.println(new String(inBuff, 0, inPacket.getLength()));
					// 从字符串中取出一个元素作为发送数据
					byte[] sendData = book[i % 4].getBytes();
					// 已指定的字符数组作为发送数据，以刚接手到的datagramPacket的
					// 源socketAddress作为目标socketAddress创建DatagramPacket
					outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());// 通过这个getSocketAddress就可以得到相应的IP地址和端口了
					// 发送数据
					socket.send(outPacket);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new UDPServer().init();
	}
}