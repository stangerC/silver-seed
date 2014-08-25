package com.silver.seed.configuration.descriptor;

import java.net.URL;

/**
 *
 * @author Liaojian
 */
public class ConfigurationDescriptor{
    private URL source;
    private String name;
    private String type;
    
    public ConfigurationDescriptor() {        
    }
    
    public ConfigurationDescriptor(String name, String type, URL source) {
        this.name = name;
        this.type = type;
        this.source = source;
    }

    public URL getSource() {
        return source;
    }

    public void setSource(URL source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }        
}
