package com.silver.seed.configuration.wrapper;

import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;

import org.apache.commons.configuration.Configuration;

/**
 *
 * @author Liaojian
 */
public class ConfigurationWrapper<T extends Configuration> {
    private T configuration;
    private ConfigurationDescriptor descriptor;

    public T getConfiguration() {
        return configuration;
    }

    public void setConfiguration(T configuration) {
        this.configuration = configuration;
    }

    public ConfigurationDescriptor getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(ConfigurationDescriptor descriptor) {
        this.descriptor = descriptor;
    }        
}
