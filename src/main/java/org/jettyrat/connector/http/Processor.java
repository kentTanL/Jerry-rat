package org.jettyrat.connector.http;

public interface Processor {
    
    public void process(HttpRequest request, HttpResponse response);

}
