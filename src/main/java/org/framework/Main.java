package org.framework;

import com.sun.net.httpserver.Headers;
import org.framework.config.RestXmlParser;
import org.framework.core.ResponseGenerator;
import org.framework.properties.MockResponse;
import org.framework.config.ServiceConfig;
import org.framework.core.AbstractService;
import org.framework.core.BaseRoute;
import org.framework.core.impl.GroovyScriptResponseGenerator;
import org.framework.core.impl.MockRequestHandler;
import org.framework.services.rest.RestRoute;
import org.framework.services.rest.RestService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        try{
            File file = new File("/Users/paraggarg/IdeaProjects/virtserver/src/main/resources/input.xml");
            RestXmlParser parser = new RestXmlParser();
            List<BaseRoute> routes = parser.parseRestRoutes(file);
            ServiceConfig config = new ServiceConfig();
            config.setRoutes(routes);
            config.setPort(8080);
            config.setRequestHandler(new MockRequestHandler(config.getRoutes()));

            AbstractService restService = new RestService(config);
            restService.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

   static List<MockResponse> getResponses(){
        Headers headers = new Headers();
        headers.add("content-type", "text/html");
        MockResponse r1 = new MockResponse("successResponse",headers,200, "Hello There".getBytes());
        MockResponse r2 = new MockResponse("errorResponse",headers,400, "Hello how are you".getBytes());
        List<MockResponse> mylist  = new ArrayList<>();
        mylist.add(r1);
        mylist.add(r2);
        return mylist;
    }

    static ResponseGenerator getResponseGenerator(String script){
        return new GroovyScriptResponseGenerator(script);
    }

    String getScript() throws IOException {
        String filePath = getClass().getClassLoader().getResource("main1.groovy").getFile();
        String fileContent = Files.readString(Paths.get(filePath));
        return fileContent;
    }

}