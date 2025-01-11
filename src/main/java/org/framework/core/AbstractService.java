package org.framework.core;

import org.framework.config.ServiceConfig;
import org.xml.sax.HandlerBase;

public abstract class AbstractService {
    protected ServiceConfig config;

    public AbstractService(ServiceConfig config) {
        this.config = config;
    }

    public abstract void start();
    public abstract void stop();
}
