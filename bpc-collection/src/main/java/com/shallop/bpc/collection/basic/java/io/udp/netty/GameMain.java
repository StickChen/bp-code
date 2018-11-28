package com.shallop.bpc.collection.basic.java.io.udp.netty;

/**
 * @author StickChen
 * @date 2017/1/14
 */

import com.shallop.bpc.collection.basic.java.io.udp.netty2.EchoSeverHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author TinyZ on 2015/6/8.
 */
public class GameMain {

	public static void main(String[] args) throws InterruptedException {

		final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioDatagramChannel.class);
		bootstrap.group(nioEventLoopGroup);
		bootstrap.handler(new ChannelInitializer<NioDatagramChannel>() {

			@Override
			public void channelActive(ChannelHandlerContext ctx) throws Exception {
				super.channelActive(ctx);
			}

			@Override
			protected void initChannel(NioDatagramChannel ch) throws Exception {
				ChannelPipeline cp = ch.pipeline();
				cp.addLast("framer", new MessageToMessageDecoder<DatagramPacket>() {
					@Override
					protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
						out.add(msg.content().toString(Charset.forName("UTF-8")));
					}
				}).addLast("handler", new EchoSeverHandler());
			}
		});
		// 监听端口 UDP服务器使用的是bind方法，来监听端口
		ChannelFuture sync = bootstrap.bind(9009).sync();
		Channel udpChannel = sync.channel();

		// String data = "我是大好人啊";
		// udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(data.getBytes(Charset.forName("UTF-8"))), new
		// InetSocketAddress("192.168.2.29", 9008)));

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				nioEventLoopGroup.shutdownGracefully();
			}
		}));
	}

}