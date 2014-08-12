package com.silver.seed.configuration.reopsitory;

import com.silver.seed.configuration.ConfigurationService;
import com.silver.seed.configuration.builder.DescriptorConfigurationBuilder;
import com.silver.seed.configuration.builder.PropertiesConfigurationBuilder;
import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;
import com.silver.seed.configuration.exception.ConfigurationErrorCode;
import com.silver.seed.configuration.wrapper.ConfigurationWrapper;
import com.silver.wheel.common.exception.CodedRuntimeException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.BuilderParameters;
import org.apache.commons.configuration.builder.PropertiesBuilderParametersImpl;
import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.io.FileHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Liaojian
 */
public class ConfigurationRepository extends BasicConfigurationRepository{        

    private Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders = new HashMap<>();

    public Map<Class<? extends Configuration>, BasicConfigurationBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders) {
        this.builders = builders;
    }

    public ConfigurationRepository() {
    }

    public ConfigurationRepository( Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders) {
        this.builders = builders;
    }
    
    @Override
    protected <T extends Configuration> ConfigurationWrapper<T>  createConfigurationInternal(String configName, Class<T> clazz,
            BuilderParameters parameters) {

        BasicConfigurationBuilder<T> builder = builders.get(clazz);

        if (builder != null) {
            try {
                builder.reset();
                builder.configure(parameters);
                
                T config = builder.getConfiguration();                                                
                
                ConfigurationWrapper wrapper = new ConfigurationWrapper();
                wrapper.setConfiguration(config);
                
                ConfigurationDescriptor descriptor = null;            
                if(builder instanceof DescriptorConfigurationBuilder) {
                    descriptor = ((DescriptorConfigurationBuilder)builder).createDescriptor(configName, parameters);
                }
                wrapper.setDescriptor(descriptor);
                
                return wrapper;
            } catch (ConfigurationException ex) {
                throw new CodedRuntimeException(ConfigurationErrorCode.APACHE_CONFIGURATION, ex);
            }
        } else {
            throw new CodedRuntimeException(ConfigurationErrorCode.CONFIGURATION_BUILDER_DOES_NOT_EXIST, 
                    String.format("configuration builder for [%s] is not existing!", clazz));            
        }
    }
            
    
    /*
    public Configuration updateConfiguration(String configName) {
        Configuration configuration = repository.getConfiguration(configName);
        if (configuration != null) {
            return configuration;
        } else {
            return null;
        }
    }
    
    public Map<String, Configuration> getConfigurations() {
        Map<String, Configuration> configMap = new HashMap<String, Configuration>();
        List<String> nameList = repository.getConfigurationNameList();

        for (String name : nameList) {
            configMap.put("name", repository.getConfiguration(name));
        }

        return configMap;
    }
    */

    public static void main(String[] args) throws IOException, ConfigurationException {                 
        PropertiesBuilderParametersImpl params = new PropertiesBuilderParametersImpl();  
        params.setBeanHelper(BeanHelper.INSTANCE);
        
        FileHandler handler = params.getFileHandler();   
        handler.setURL(Thread.currentThread().getContextClassLoader().getResource("config/test/app.properties")); 

        Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders = new HashMap<>();
        builders.put(PropertiesConfiguration.class, new PropertiesConfigurationBuilder(PropertiesConfiguration.class));
        ConfigurationRepository repos = new ConfigurationRepository(builders);

        PropertiesConfiguration config = repos.createConfiguration("app", PropertiesConfiguration.class, params);
        System.out.println(config);
        System.out.println(config.getString("app.name"));
        
        params = new PropertiesBuilderParametersImpl();  
        params.setBeanHelper(BeanHelper.INSTANCE);
        
        handler = params.getFileHandler();   
        handler.setURL(Thread.currentThread().getContextClassLoader().getResource("config/test/db.properties")); 
        config = repos.createConfiguration("db", PropertiesConfiguration.class, params);
        System.out.println(config);
        System.out.println(config.getString("db.name"));
        
        System.out.println(repos.getString("db.type"));
    }

    
}
