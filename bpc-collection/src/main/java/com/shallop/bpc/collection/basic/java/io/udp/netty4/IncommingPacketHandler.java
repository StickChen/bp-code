package com.shallop.bpc.collection.basic.java.io.udp.netty4; /**
 * Created by mahesh.govind on 2/5/16.
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;


public class IncommingPacketHandler extends  SimpleChannelInboundHandler<DatagramPacket> {

    IncommingPacketHandler( String  parserServer){

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        final int rcvPktLength = buf.readableBytes();
        final byte[] rcvPktBuf = new byte[rcvPktLength];
        buf.readBytes(rcvPktBuf);
        System.out.println("Inside incomming packet handler");

        //rcvPktProcessing(rcvPktBuf, rcvPktLength, srcAddr);
    }

}
