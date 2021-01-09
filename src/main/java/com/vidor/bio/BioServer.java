package com.vidor.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class BioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket sc = new ServerSocket(8000);
        while (true) {
            final Socket socket = sc.accept();
            System.out.println("接收到连接请求:"+socket.getLocalSocketAddress());
            new Thread(() -> {
                handle(socket);
            }).start();
        }
    }

    private static void handle(Socket socket) {
        String msg = "连接成功";
        try {
            socket.getOutputStream().write(msg.getBytes(Charset.defaultCharset()));
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
