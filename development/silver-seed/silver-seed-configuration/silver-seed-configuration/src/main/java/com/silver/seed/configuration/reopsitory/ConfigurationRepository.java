package com.silver.seed.configuration.reopsitory;

import com.silver.seed.configuration.ConfigurationService;
import com.silver.seed.configuration.builder.DescriptorConfigurationBuilder;
import com.silver.seed.configuration.builder.PropertiesConfigurationBuilder;
import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;
import com.silver.seed.configuration.exception.ConfigurationErrorCode;
import com.silver.seed.configuration.wrapper.ConfigurationWrapper;
import com.silver.wheel.common.exception.CodedRuntimeException;
import org.apache.commons.configuration.BaseConfiguration;
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

    /**
     * 配置构建器容器，配置仓库用到builder会放到其中。
     */
    private Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders = new HashMap<>();

    public Map<Class<? extends Configuration>, BasicConfigurationBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders) {
        this.builders = builders;
    }

    /**
     * 添加构建器到仓库内部。如果配置的类型对应的构建器已经存在，则将会被替换掉。
     * @param builder
     */
    public void addBuilder(BasicConfigurationBuilder builder) {
        this.builders.put(builder.getResultClass(), builder);
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

                //调用reset方法，释放构建器上次创建的配置。如果不调用，那么getConfiguration方法将会返回上次
                //创建的配置对象。
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
            
    public PropertiesConfiguration createPropertiesConfiguration(String configName, String path) {
        PropertiesBuilderParametersImpl parameters = new PropertiesBuilderParametersImpl();
        parameters.setBeanHelper(BeanHelper.INSTANCE);

        FileHandler handler = parameters.getFileHandler();
        handler.setURL(Thread.currentThread().getContextClassLoader().getResource(path));

        return createConfiguration(configName, PropertiesConfiguration.class, parameters);
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
    
}
