package org.framework.core;

import org.framework.properties.MockResponse;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;

import java.util.List;

// dispatch style
// mock responses
public abstract class BaseRoute {
  ResponseGenerator generator;
  List<MockResponse> responses;

  String defaultResponse;
  public BaseRoute(ResponseGenerator generator, List<MockResponse> responses, String defaultResponse) {
    this.generator = generator;
    this.responses = responses;
    this.defaultResponse = defaultResponse;
  }


  public MockResponse geResponse(Context context, MockRequest request) {
    String response =  generator.GenerateResponse(context, request);
    if (response == null){
      return getDefaultResponse();
    }

    for (MockResponse resp : responses) {
      if (resp.getName().equals(response)) {
        return resp;
      }
    }

    return getDefaultResponse();
  }

  public List<MockResponse> getResponses() {
    return responses;
  }

  public MockResponse getDefaultResponse() {
    for (MockResponse response: responses){
      if (response.getName().equals(defaultResponse)){
        return response;
      }
    }
    return responses.getFirst();
  }
  public abstract boolean matchRequest(MockRequest request);
}
