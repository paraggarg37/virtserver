package org.framework.config;


import com.sun.net.httpserver.Headers;
import org.apache.tools.ant.taskdefs.email.Header;
import org.framework.core.BaseRoute;
import org.framework.core.ResponseGenerator;
import org.framework.core.impl.GroovyScriptResponseGenerator;
import org.framework.properties.MockResponse;
import org.framework.services.rest.RestRoute;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestXmlParser {

    public List<BaseRoute> parseRestRoutes(File xmlFile) throws Exception {
        List<BaseRoute> routes = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        NodeList restMockActions = document.getElementsByTagName("con:restmockAction");

        for (int i = 0; i < restMockActions.getLength(); i++) {
            Element action = (Element) restMockActions.item(i);

            String resourcePath = action.getAttribute("resourcePath");
            String method = action.getAttribute("method");
            NodeList dispatchStyleNodes = action.getElementsByTagName("con:dispatchPath");
            String script = null;
            if (dispatchStyleNodes.getLength() > 0) {
                if (dispatchStyleNodes.item(0) != null)
                     script = dispatchStyleNodes.item(0).getTextContent().trim();
            }

            
            List<MockResponse> mockResponses = parseMockResponses(action);

            // Initialize Route with ResponseGenerator and mock responses
            ResponseGenerator generator = new GroovyScriptResponseGenerator(script); // You can customize this
            BaseRoute route = new RestRoute(generator, mockResponses, "DefaultResponse",resourcePath,method);
            routes.add(route);
        }

        return routes;
    }

    private List<MockResponse> parseMockResponses(Element action){

            List<MockResponse> mockResponses = new ArrayList<>();

            NodeList responseNodes = action.getElementsByTagName("con:response");
            for (int j = 0; j < responseNodes.getLength(); j++) {
                Element responseNode = (Element) responseNodes.item(j);

                String name = responseNode.getAttribute("name");
                int statusCode = Integer.parseInt(responseNode.getAttribute("httpResponseStatus"));
                String mediaType = responseNode.getAttribute("mediaType");

                // Extract response body from <con:responseContent>
                String responseBody = "";
                NodeList responseContentNodes = responseNode.getElementsByTagName("con:responseContent");
                if (responseContentNodes.getLength() > 0) {
                    responseBody = responseContentNodes.item(0).getTextContent().trim();
                }

                // Parse headers
                Headers headers = parseHeaders(mediaType);

                // Create MockResponse object
                MockResponse mockResponse = new MockResponse(name, headers, statusCode, responseBody.getBytes());
                mockResponses.add(mockResponse);
            }

            return mockResponses;
        }

    private Headers parseHeaders(String mediaType) {
        Headers headers = new Headers();
        headers.add("Content-Type", mediaType);
        return headers;
    }


    private String getResponseAttribute(Element action, String attributeName) {
        NodeList responses = action.getElementsByTagName("con:responseContent");
        if (responses.getLength() > 0) {
            Element response = (Element) responses.item(0);
            return response.getAttribute(attributeName);
        }
        return null;
    }

    private String getResponseBody(Element action) {
        NodeList responses = action.getElementsByTagName("con:responseContent");
        if (responses.getLength() > 0) {
            Element response = (Element) responses.item(0);
            return response.getTextContent().trim();
        }
        return null;
    }
}

