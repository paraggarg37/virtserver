package org.framework.services.rest;

import org.framework.properties.MockResponse;
import org.framework.core.BaseRoute;
import org.framework.core.ResponseGenerator;
import org.framework.properties.MockRequest;

import java.util.List;

public class RestRoute extends BaseRoute {

    String method;
    String path;

    public RestRoute(ResponseGenerator generator, List<MockResponse> responses, String defaultResponse, String path, String method) {
        super(generator, responses, defaultResponse);
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean matchRequest(MockRequest request) {
        return request.getPath().equals(path) && request.getMethod().equals(method);
    }
}
