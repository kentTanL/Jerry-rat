package org.jettyrat.startup;

import org.jettyrat.connector.http.HttpConnector;

public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}
