package com.silver.seed.configuration;

import com.silver.seed.exception.CodeRuntimeException;
import com.silver.seed.pattern.factory.VariableFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Liaojian
 */
public class PropertiesConfigurationFactory implements VariableFactory<PropertiesConfiguration, String> {        
    private Map<String, PropertiesConfiguration> map = new HashMap<String, PropertiesConfiguration>();
    
    public PropertiesConfiguration create(String propertiesFilePath) {
        PropertiesConfiguration configuration = map.get(propertiesFilePath);
        
        if(configuration == null) {
            try {
                configuration = new PropertiesConfiguration(propertiesFilePath);
            } catch (ConfigurationException ex) {
                throw new CodeRuntimeException(ex);
            }
            map.put(propertiesFilePath, configuration);
        }
        
        return configuration;
    } 
}
