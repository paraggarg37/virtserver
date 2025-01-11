package org.framework.properties;

import com.sun.net.httpserver.Headers;

public class MockResponse {
    // httpResponse


    public MockResponse(String name, Headers headers, int statusCode, byte[] responseBytes) {
        this.name = name;
        this.headers = headers;
        this.statusCode = statusCode;
        this.responseBytes = responseBytes;
    }

    String name;
    Headers headers;
    int statusCode;

    byte[] responseBytes;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }

    public void setResponseBytes(byte[] responseBytes) {
        this.responseBytes = responseBytes;
    }
}
