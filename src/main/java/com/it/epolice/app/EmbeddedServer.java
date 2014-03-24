package com.it.epolice.app;

import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class EmbeddedServer {

    private final static String ACCESS_LOG_FILE = "environment.access.log";
    private final static String PORT = "environment.port";
    private final static String DEBUG = "environment.debug";


    public static void main(String[] args) throws Exception {
        Server server = new Server(Integer.parseInt("8090"));

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        ProtectionDomain protectionDomain = EmbeddedServer.class.getProtectionDomain();
        URL location = protectionDomain.getCodeSource().getLocation();

        if (EmbeddedServer.readEnvironmentVariable(DEBUG, "").length() > 0){
            webAppContext.setWar("src/main/webapp");
        }
        else{
            webAppContext.setWar(location.toExternalForm());
        }
        server.setHandler(webAppContext);

        NCSARequestLog log = new NCSARequestLog(readEnvironmentVariable(ACCESS_LOG_FILE,"it-feed.log"));
        RequestLogHandler requestLog = new RequestLogHandler();
        requestLog.setRequestLog(log);
        webAppContext.setHandler(requestLog);

        server.start();
        server.join();

    }

    private static String readEnvironmentVariable(String env, String defaultValue) {
        String propertyFileName = System.getProperty(env, defaultValue);
        System.out.println("The " + env + " is specified " + propertyFileName);
        return propertyFileName;
    }

}