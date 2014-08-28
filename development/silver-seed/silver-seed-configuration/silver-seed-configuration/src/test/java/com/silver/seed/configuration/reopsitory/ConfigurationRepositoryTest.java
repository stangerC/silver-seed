package com.silver.seed.configuration.reopsitory;

import com.silver.seed.configuration.builder.PropertiesConfigurationBuilder;
import junit.framework.TestCase;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.PropertiesBuilderParametersImpl;
import org.apache.commons.configuration.io.FileHandler;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationRepositoryTest extends TestCase {

    public void testCreateConfigurationInternal() throws Exception {
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