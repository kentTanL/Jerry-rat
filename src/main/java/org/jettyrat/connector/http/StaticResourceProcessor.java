package org.jettyrat.connector.http;

import java.io.IOException;

public class StaticResourceProcessor implements Processor {

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
