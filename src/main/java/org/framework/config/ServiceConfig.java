package org.framework.config;

import org.framework.core.BaseRoute;
import org.framework.core.EventHandler;
import org.framework.core.RequestHandler;
import org.framework.core.RouteMatcher;

import java.util.List;
import java.util.regex.Matcher;

public class ServiceConfig {
    private String name;
    private int port;
    private String protocol;
    private List<BaseRoute> routes; // For REST, TCP, SOAP

    private RequestHandler requestHandler;
    private EventHandler eventHandler;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<BaseRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<BaseRoute> routes) {
        this.routes = routes;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler handler) {
        this.eventHandler = handler;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }
}

