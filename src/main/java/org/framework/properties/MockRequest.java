package org.framework.properties;

import com.sun.net.httpserver.Headers;

public class MockRequest {
    Headers requestHeaders;
    String requestContent;
    String path;

    String queryString;
    String method;

    String protocol;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Headers getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Headers requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
