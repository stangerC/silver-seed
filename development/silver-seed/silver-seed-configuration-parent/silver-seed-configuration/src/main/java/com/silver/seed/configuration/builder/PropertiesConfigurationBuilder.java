package com.silver.seed.configuration.builder;

import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;
import com.silver.wheel.common.exception.CodedRuntimeException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.builder.BuilderParameters;
import org.apache.commons.configuration.builder.PropertiesBuilderParametersImpl;
import org.apache.commons.configuration.builder.ReloadingFileBasedConfigurationBuilder;

import java.util.Map;

/**
 *
 * @author Liaojian
 */
public class PropertiesConfigurationBuilder extends 
        ReloadingFileBasedConfigurationBuilder<PropertiesConfiguration> implements 
        DescriptorConfigurationBuilder{
    
    public PropertiesConfigurationBuilder(Class resCls) {
        super(resCls);
    }
    
    public PropertiesConfigurationBuilder(Class resCls,
            Map<String, Object> params) {
        super(resCls, params);
    }
    
    public PropertiesConfigurationBuilder(Class resCls,
            Map<String, Object> params, boolean allowFailOnInit) {
        super(resCls, params, allowFailOnInit);
    }

    @Override
    public ConfigurationDescriptor createDescriptor(String configName, BuilderParameters parameters) {        
        if(parameters instanceof PropertiesBuilderParametersImpl) {
            ConfigurationDescriptor descriptor = new ConfigurationDescriptor(
                    configName, "PropertiesConfiguration", ((PropertiesBuilderParametersImpl)parameters).getFileHandler().getURL());
            
            return descriptor;
        } else {
            throw new CodedRuntimeException("PropertiesConfigurationBuilder need PropertiesBuilderParametersImpl type parameter");
        }
    }
}
