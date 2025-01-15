package org.framework.core;

import org.framework.core.expansion.TemplateReplacer;
import org.framework.core.impl.MockResponseProcessor;
import org.framework.core.impl.PropertyResolver;
import org.framework.properties.MockResponse;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
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


  public MockResponse geResponse(Context context, MockRequest request) throws UnsupportedEncodingException {
    String response =  generator.GenerateResponse(context, request);
    System.out.println(context.mockService.getMockServiceProperties());
    if (response == null){
      return getDefaultResponse();
    }

    for (MockResponse resp : responses) {
      if (resp.getName().equals(response)) {
        String resolvedBody = resolvePlaceholders(new String (resp.getResponseBytes(), "UTF-8"), context);
        resp.setResponseBytes(resolvedBody.getBytes(StandardCharsets.UTF_8));
        return resp;
      }
    }

    return getDefaultResponse();
  }

  private String resolvePlaceholders(String responseBody, Context context) {
    TemplateReplacer replacer = new TemplateReplacer();
    replacer.addPropertyMap("MockService", context.getMockService().getMockServiceProperties());
    replacer.addPropertyMap("", context.getDynamicProperties());
    return replacer.replaceTemplate(responseBody);

//    try{
//      PropertyResolver resolver = new PropertyResolver(context); // Use the resolver logic shared earlier
//      // Load dynamic properties from mockService into the resolver
//      context.mockService.getMockServiceProperties().forEach((key, value) -> {
//        resolver.setMockServiceProperty(key, String.valueOf(value));
//      });
//
//      // Process and resolve placeholders in the response body
//      MockResponseProcessor processor = new MockResponseProcessor(resolver);
//      return processor.processMockResponse(responseBody);
//    }
//    catch(Exception e){
//      e.printStackTrace();
//
//    }
//    return null;
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
