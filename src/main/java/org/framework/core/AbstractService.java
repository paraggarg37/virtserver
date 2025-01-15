package org.framework.core;

import org.framework.config.ServiceConfig;
import org.xml.sax.HandlerBase;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractService {
    protected ServiceConfig config;

    private final Map<String, Object> mockServiceProperties = new HashMap<>();
    public String address = "123";
    // Method to set a dynamic property
    public void setPropertyValue(String name, Object value) {
        System.out.println("i am in setter");
        mockServiceProperties.put(name, value);
    }

    // Method to get a dynamic property
    public Object getPropertyValue(String name) {
        return mockServiceProperties.get(name);
    }

    public Map<String, Object> getMockServiceProperties() {
        return mockServiceProperties;
    }

    public AbstractService(ServiceConfig config) {
        this.config = config;
    }

    public abstract void
    start();
    public abstract void stop();
}
