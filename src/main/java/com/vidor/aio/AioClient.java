package com.vidor.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class AioClient {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        AsynchronousSocketChannel sc = AsynchronousSocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 9001)).get();
        sc.write(ByteBuffer.wrap("Hello Server".getBytes()));
        ByteBuffer bf = ByteBuffer.allocate(0124);
        Integer len = sc.read(bf).get();
        if (len != -1) {
            System.out.println("收到服务端数据： "+new String(bf.array(), 0, len));
        }
    }
}
