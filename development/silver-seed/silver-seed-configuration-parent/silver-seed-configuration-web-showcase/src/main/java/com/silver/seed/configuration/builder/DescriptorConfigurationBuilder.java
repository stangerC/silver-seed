package com.silver.seed.configuration.builder;

import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;

import org.apache.commons.configuration.builder.BuilderParameters;

/**
 *
 * @author Liaojian
 */
public interface DescriptorConfigurationBuilder {
    public ConfigurationDescriptor createDescriptor(String configName, BuilderParameters parameters);
}
