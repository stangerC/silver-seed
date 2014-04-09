package com.silver.seed.configuration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.silver.wheel.common.pattern.factory.VariableFactory;
import com.silver.wheel.common.exception.CodedRuntimeException;

/**
 * 属性文件配置工厂类。用于创建、存储属性文件配置。
 * @author Liaojian
 * @since 0.0.2
 */
public class PropertiesConfigurationFactory implements VariableFactory<PropertiesConfiguration, String> {        
    private Map<String, PropertiesConfiguration> configurations = new HashMap<String, PropertiesConfiguration>();
    /**
     * 根据给定的属性文件路径创建PropertiesConfiguration对象。如果曾经使用相同的属性文件，则将会返回
     * 上次创建的PropertiesConfiguration对象。
     */
    public PropertiesConfiguration create(String resourceName) {
        PropertiesConfiguration configuration = configurations.get(resourceName);
        
        if(configuration == null) {
            try {
                URL resourceURL = Thread.currentThread().getContextClassLoader().getResource(resourceName);                
                configuration = new PropertiesConfiguration(resourceURL);                
            } catch (ConfigurationException ex) {
                throw new CodedRuntimeException(ex);
            } 
            configurations.put(resourceName, configuration);
        }
        
        return configuration;
    } 
}
