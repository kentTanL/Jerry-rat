package org.jettyrat.net.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    private volatile boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    public void await() {
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
        
        // ѭ���ȴ�request����
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // ����Request���󲢽���
                Request request = new Request(input);
                request.parse();

                // ����Response����
                Response response = new Response(output);
                response.setReqeust(request);
                
                Processor processor = null;
                // ������������servlet���߾�̬��Դ����
                // servlet������"/servlet/"��ʼ
                if (request.getUri().startsWith("/servlet")) {
                    processor = new ServletProcessor();
                } else {
                    processor = new StsticResourceProcessor();
                }
                
                processor.process(request, response);
                
                // close
                socket.close();

                // �Ƿ���Ҫ�رշ�����
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                continue;
            }
        }

    }

}
