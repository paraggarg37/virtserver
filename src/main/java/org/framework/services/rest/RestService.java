package org.framework.services.rest;

import com.sun.net.httpserver.*;
import org.framework.properties.MockResponse;
import org.framework.config.ServiceConfig;
import org.framework.core.AbstractService;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestService extends AbstractService {
    private  HttpServer server;

    private ExecutorService executor;
    public RestService(ServiceConfig config) throws IOException {
        super(config);
        server = HttpServer.create(new InetSocketAddress(config.getPort()), 0);
    }

    @Override
    public void start() {
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
        server.setExecutor(executor);
        server.start();
        AbstractService that = this;

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                Context context = new Context(that);
                    MockResponse response = config.getRequestHandler().handleRequest(context, getRequest(exchange));

                   //setHeaders
                    Headers headers = response.getHeaders();
                    for (String key : headers.keySet()){
                        List<String> values = headers.get(key);
                        for (String value : values) {
                            exchange.getResponseHeaders().add(key, value);
                        }
                    }

                    exchange.sendResponseHeaders(response.getStatusCode(), response.getResponseBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getResponseBytes());
                    os.close();
            }
        });
    }

    MockRequest getRequest(HttpExchange exchange) throws IOException {
        MockRequest mockRequest  = new MockRequest();

        mockRequest.setMethod(exchange.getRequestMethod());
        mockRequest.setRequestHeaders(exchange.getRequestHeaders());
        mockRequest.setPath(exchange.getRequestURI().getPath());
        mockRequest.setQueryString(exchange.getRequestURI().getQuery());
        mockRequest.setRequestContent(getRequestBody(exchange));
        return mockRequest;
    }
    public static String getRequestBody(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder body = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            body.append(line);
        }

        reader.close();
        return body.toString();
    }

    @Override
    public void stop() {
        server.stop(0);
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }
}
