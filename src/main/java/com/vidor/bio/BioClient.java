package com.vidor.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(8000));
        byte[] bt = new byte[1024];
        socket.getInputStream().read(bt);
        System.out.println(new String(bt));
    }
}
