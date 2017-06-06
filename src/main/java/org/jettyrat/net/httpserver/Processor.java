package org.jettyrat.net.httpserver;

public interface Processor {
    
    public void process(Request request, Response response);

}
