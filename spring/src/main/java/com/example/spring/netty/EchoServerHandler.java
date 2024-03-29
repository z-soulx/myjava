package com.example.spring.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @program: myjava
 * @description:
 * @author: soulx
 * @create: 2023-12-12 19:11
 **/
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx,
			Object msg)  throws Exception  {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8) + " from channel " + ctx.channel().hashCode());
		ctx.write(in);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		System.out.println("Read complete for channel " + ctx.channel().hashCode());
		// keep channel busy forever
		while(true);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,
			Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
