package org.framework.core.impl;

import com.sun.net.httpserver.Headers;
import org.framework.properties.MockResponse;
import org.framework.core.BaseRoute;
import org.framework.core.RequestHandler;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;
import org.framework.utils.Logger;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MockRequestHandler implements RequestHandler {

    private final List<BaseRoute> routes;

    public MockRequestHandler(List<BaseRoute> routes) {
        this.routes = routes;
    }

    @Override
    public MockResponse handleRequest(Context context, MockRequest request) {

        String method = request.getMethod();
        String path = request.getPath();

        Logger.getInstance().info("Method : " + method);
        Logger.getInstance().info("Path : " + path);


        for (BaseRoute route : routes) {
            if (route.matchRequest(request)) {
                System.out.println("request matched");
                return route.geResponse(context, request);
            }
        }

        Headers headers = new Headers();
        headers.add("content-type", "plain/text");
        String out = "Page not found";

        return new MockResponse("defaultResponse",headers, 404, out.getBytes(StandardCharsets.UTF_8));
    }
}
