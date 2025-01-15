package org.framework.core;

import org.framework.properties.MockResponse;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;

import java.io.UnsupportedEncodingException;

public interface RequestHandler {
     MockResponse handleRequest(Context context, MockRequest request) throws UnsupportedEncodingException; // Generalized request object
}