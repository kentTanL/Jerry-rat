package org.jettyrat.net.httpserver;

import java.io.IOException;

public class StsticResourceProcessor implements Processor {

    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
