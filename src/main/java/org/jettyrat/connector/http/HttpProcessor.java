package org.jettyrat.connector.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;

public class HttpProcessor {
    private HttpRequestLine requestLine = null;
    private HttpRequest request = null;
    private HttpResponse response = null;
    
    public void process(Socket socket) throws ServletException {
        SocketInputStream input = null;
        OutputStream output = null;
        
        try {
            input = new SocketInputStream(socket.getInputStream(), 2048);
            output = socket.getOutputStream();

            // 创建HttpRequest对象并解析
            request = new HttpRequest(input);
            // request.parse();

            // 创建Response对象
            response = new HttpResponse(output);
            response.setReqeust(request);
            
            response.setHeader("Server", "Jettyrat Servlet Container");
            
            parseRequest(input, output);
            parseHeaders(input);
            
            Processor processor = null;
            // 检查这个请求是servlet或者静态资源请求
            // servlet请求以"/servlet/"开始
            if (request.getRequestURI().startsWith("/servlet")) {
                processor = new ServletProcessor();
            } else {
                processor = new StaticResourceProcessor();
            }
            
            processor.process(request, response);
            
            // close
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void parseRequest(SocketInputStream input, OutputStream output) throws ServletException {
        // Parse the incoming request line
        input.readRequestLine(requestLine);
        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);
        
        // Validate the incoming request line
        if (method.length() < 1) {
            throw new ServletException("Missing HTTP request method");
        } else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request URI");
        }
        
        // Parse any query parameters out od the request URI
        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1, requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(new String(requestLine.uri, 0, requestLine.uriEnd));
        }
        
        // Checking for an absolute URI (with the HTTP protocol)
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            // Parsing out protocol and host name
            if (pos != -1) {
                pos = uri.indexOf('/', pos + 3);
                if (pos == -1) {
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }
        
        // Parse any requested session ID out of the request URI
        String match = ";jessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(";");
            if (semicolon2 >= 0) {
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                request.setRequestedSessionId(rest);
                rest = "";
            }
            
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }
        
        // Normalize URI (using String operations at the moment)
        String normalizedUri = normalize(uri);
        // Set the corresponding request properties
        ((HttpRequest) request).setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            ((HttpRequest) request).setRequestURI(normalizedUri);
        } else {
            ((HttpRequest) request).setRequestURI(uri);
        } 
        
        if (normalizedUri == null) {
            throw new ServletException("Invalid URI: '" + uri + "'");
        }
    }
    
    private String normalize(String uri) {
        // TODO Auto-generated method stub
        return null;
    }


    private void parseHeaders(SocketInputStream input) {
        // TODO Auto-generated method stub
    }


}
