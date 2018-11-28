package com.shallop.bpc.collection.basic.java.io.udp.netty3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * udp服务端
 * 
 * @author wuminchao
 *
 */
public class UDPServer {

	private EventLoopGroup group;

	/**
	 * 服务端启动
	 * 
	 * @param port
	 *            启动端口
	 * @throws Exception
	 */
	public void run(int port) throws Exception {
		// nio线程组
		group = new NioEventLoopGroup();
		try {
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
				.handler(new UDPServerHandler());
			b.bind(port).sync().channel().closeFuture().await();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		// 初始化端口参数
		int port = 8080;
		if (args.length > 0) {
			try {
			port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		new UDPServer().run(port);
		// 服务端启动
	}

}
