package org.framework.core;

import org.framework.properties.MockResponse;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;

public interface RequestHandler {
     MockResponse handleRequest(Context context, MockRequest request); // Generalized request object
}