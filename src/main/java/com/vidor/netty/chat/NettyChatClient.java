package com.vidor.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class NettyChatClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder())
                            .addLast(new StringEncoder())
                            .addLast(new ChatClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(9003)).sync();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    channelFuture.channel().writeAndFlush(scanner.nextLine());
                }
            }
//            channelFuture.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
