package com.vidor.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String online = channel.remoteAddress()+"上线";
        System.out.println(online);
        channelGroup.writeAndFlush(online);
        channelGroup.add(channel);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel current = ctx.channel();
        String message = "收到来自" + "[" + current.remoteAddress() + "]:" + msg;
        System.out.println("服务端消息备份：" + message);
        // peek结果还是流
        channelGroup.stream().filter(channel -> !channel.equals(current)).forEach(channel -> {
            channel.writeAndFlush(message);
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String offline = ctx.channel().remoteAddress()+"下线";
        System.out.println("服务端消息备份：" + offline);
        channelGroup.writeAndFlush(offline);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
//        ctx.fireExceptionCaught(cause);
    }
}
