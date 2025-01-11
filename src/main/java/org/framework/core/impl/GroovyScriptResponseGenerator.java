package org.framework.core.impl;

import org.framework.core.ResponseGenerator;
import org.framework.properties.Context;
import org.framework.properties.MockRequest;

import groovy.lang.GroovyShell;
import groovy.lang.Binding;
import org.framework.utils.Logger;

public class GroovyScriptResponseGenerator implements ResponseGenerator {

    String script;
    public GroovyScriptResponseGenerator(String script) {
        this.script= script;
    }

    @Override
    public String GenerateResponse(Context context, MockRequest request) {
        Binding binding = new Binding();

        binding.setVariable("context", context);
        binding.setVariable("mockRequest", request);
        binding.setVariable("log", Logger.getInstance());

        GroovyShell shell = new GroovyShell(binding);
        Object object =  shell.evaluate(script);

        return (String) object;
    }
}
