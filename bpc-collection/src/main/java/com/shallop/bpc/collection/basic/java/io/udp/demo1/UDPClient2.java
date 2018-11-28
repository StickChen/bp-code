package com.shallop.bpc.collection.basic.java.io.udp.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author StickChen
 * @date 2017/1/14
 */
public class UDPClient2 implements Runnable {
	// 定义数据报的目的地
	public static final int DEST_PORT = 30001;
	public static final String DEST_IP = "127.0.0.1";
	// 定义每个数据报的最大的大小为4KB
	public static final int DATA_LEN = 4096;
	private DatagramSocket socket = null;
	// 定义接受网络数据的字节数组
	byte[] inBuff = new byte[DATA_LEN];
	// 已指定字节数组创建准备接受数据的DatagramPacket对象
	private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
	// 定义发送的DatagramPacket对象
	private DatagramPacket outPacket;
	// 定义一个字符串数组，服务器发送该数组的元素
	String[] book = new String[] { "I", "am", "Stu" };

	public UDPClient2() {
		try {
			// 创建一个客户端DatagramSocket使用随机端口
			socket = new DatagramSocket(DEST_PORT);
			// 初始化发送数据报，包含一个长度为0的字节数组
			outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName(DEST_IP), 30000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送字符串坐标strxy,用于使用ai先手的情况
	 *
	 * @param strXY
	 */
	public void sendPointXy(String strXY) {
		byte[] buff = strXY.getBytes();
		outPacket.setData(buff);// 设置数据报的字节数据
		try {
			// 发送数据报
			socket.send(outPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 进行接受坐标strXY，并进行处理进行下棋，最后进行发送
	 *
	 */
	public void receiverPointXy(String content) {
		String[] strXY = content.split(",");
		// 对数据进行处理
		// sendPointXy(chComputer.getX() + "," + chComputer.getY());
		sendPointXy(content);
	}

	/**
	 * 成为被动接受方
	 */
	public void acceptPointXy() {
		try {
			socket.receive(inPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		receiverPointXy(new String(inBuff, 0, inPacket.getLength()));
	}

	@Override
	public void run() {
		// 死循环不断地进行监听
		while (true) {
			acceptPointXy();
		}
	}

}
