package org.framework.properties;

import groovy.lang.GroovyObjectSupport;
import org.framework.core.AbstractService;

import java.util.HashMap;
import java.util.Map;

public class Context extends GroovyObjectSupport {
    public AbstractService mockService;

    private final Map<String, Object> dynamicProperties = new HashMap<>();
    public Context(AbstractService service) {
        super();
        this.mockService = service;
    }
    public AbstractService getMockService() {
        return mockService;
    }

    // Handle dynamic properties: Groovy will use this method for context.name = value
    @Override
    public void setProperty(String name, Object value) {
        if (!"mockService".equals(name)) {
            dynamicProperties.put(name, value);
            System.out.println("Setting property: " + name + " = " + value);
        }
    }

    // Handle dynamic properties: Groovy will use this method for context.name
    @Override
    public Object getProperty(String name) {
        if (!"mockService".equals(name)) {
            System.out.println("getting property: " + name + " = " + name);
            return dynamicProperties.get(name);
        }

        return mockService;
    }

    @Override
    public Object invokeMethod(String name, Object args) {
        return this.getMetaClass().invokeMethod(this, name, args);

    }

    public Map<String, Object> getDynamicProperties() {
        return dynamicProperties;
    }
}