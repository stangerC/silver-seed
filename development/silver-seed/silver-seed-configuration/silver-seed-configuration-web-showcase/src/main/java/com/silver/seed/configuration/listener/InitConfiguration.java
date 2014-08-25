package com.silver.seed.configuration.listener;

import com.silver.seed.configuration.builder.PropertiesConfigurationBuilder;
import com.silver.seed.configuration.reopsitory.ConfigurationRepository;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.beanutils.BeanHelper;
import org.apache.commons.configuration.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration.builder.PropertiesBuilderParametersImpl;
import org.apache.commons.configuration.io.FileHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liaojian on 2014/7/7.
 */
@Service
public class InitConfiguration implements ApplicationListener<ContextRefreshedEvent>{
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ConfigurationRepository repository = event.getApplicationContext().getBean("configurationRepository", ConfigurationRepository.class);
        if(!repository.containsConfiguration("app")) {
            Map<Class<? extends Configuration>, BasicConfigurationBuilder> builders = new HashMap<Class<? extends Configuration>, BasicConfigurationBuilder>();
            builders.put(PropertiesConfiguration.class, new PropertiesConfigurationBuilder(PropertiesConfiguration.class));
            repository.setBuilders(builders);

            PropertiesBuilderParametersImpl params = new PropertiesBuilderParametersImpl();
            params.setBeanHelper(BeanHelper.INSTANCE);

            FileHandler handler = params.getFileHandler();
            handler.setURL(Thread.currentThread().getContextClassLoader().getResource("config/test/app.properties"));
            repository.createConfiguration("app", PropertiesConfiguration.class, params);
        }
    }
}
