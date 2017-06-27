package org.jettyrat.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletException;

public class HttpConnector implements Runnable {
    private volatile boolean stopped;
    private String scheme = "http";
    
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;

        try {
            System.out.println("Jettyrat starting...");
            
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            
            System.out.println("Jettyrat start successed");
        } catch (IOException e) { // UnknownHostException
            e.printStackTrace();
            System.exit(1);
        }
        
        // 循环等待request请求
        while (!stopped) {
            Socket socket = null;

            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                continue;
            }
            
            // 使用HttpProcessor处理这个socket
            HttpProcessor processer = new HttpProcessor();
            try {
                processer.process(socket);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void start() {
        Thread thread = new Thread(this);
        thread.setName("Jerryrat daemon thread");
        thread.start();
    }

    public String getScheme() {
        return scheme;
    }

}
