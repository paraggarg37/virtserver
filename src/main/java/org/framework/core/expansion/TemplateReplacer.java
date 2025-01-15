package org.framework.core.expansion;

import java.util.HashMap;
import java.util.Map;

public class TemplateReplacer {
    Map<String, Map<String, Object>> properties = new HashMap<>();

    public void addPropertyMap(String name, Map<String, Object> properties) {
        this.properties.put(name, properties);
    }

    public String replaceTemplate(String template){
        for (Map.Entry<String, Map<String, Object>> property : this.properties.entrySet()){
            for (Map.Entry<String, Object> value : property.getValue().entrySet()){
                String key = "#"+property.getKey();
                String secondKey = "#"+value.getKey();
                if (property.getKey().isEmpty()){
                    key = "";
                    secondKey = value.getKey();
                }

                template = template.replace("${"+key+secondKey+"}", value.getValue().toString());
            }
        }
        return template;
    }

}
