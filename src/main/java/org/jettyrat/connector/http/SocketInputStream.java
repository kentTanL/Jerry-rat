package org.jettyrat.connector.http;

import java.io.IOException;
import java.io.InputStream;

public class SocketInputStream extends InputStream {
    private InputStream input;
    
    public SocketInputStream(InputStream input, int i) {
        this.input = input;
    }

    public void readRequestLine(HttpRequestLine requestLine) {
        // TODO Auto-generated method stub
        
    }
    
    public void readerHeader() {}

    @Override
    public int read() throws IOException {
        return input.read();
    }
    
}
