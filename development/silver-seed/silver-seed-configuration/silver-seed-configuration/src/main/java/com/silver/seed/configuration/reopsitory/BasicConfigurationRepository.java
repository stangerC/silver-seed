/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.seed.configuration.reopsitory;

import com.silver.seed.configuration.descriptor.ConfigurationDescriptor;
import com.silver.seed.configuration.exception.ConfigurationErrorCode;
import com.silver.seed.configuration.wrapper.ConfigurationWrapper;
import com.silver.wheel.common.exception.CodedRuntimeException;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.builder.BuilderParameters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author Liaojian
 */
public abstract class BasicConfigurationRepository {
    /**
     * 内置的CombinedConfiguration，作为所有配置对象的容器。
     */
    private CombinedConfiguration container = new CombinedConfiguration();
    
    private List<ConfigurationDescriptor> descriptors = new ArrayList<ConfigurationDescriptor>();

    /**
     * 创建配置
     */
    public  <T extends Configuration> T createConfiguration(String configName, Class<T> clazz, 
            BuilderParameters parameters) {   
        if (containsConfiguration(configName)) {
                throw new CodedRuntimeException(ConfigurationErrorCode.CONFIGURATION_ALREADY_EXIST,
                        String.format("A configuration with the name %s already exists in this combined configuration!", 
                        configName));
        } else {           
            ConfigurationWrapper<T> config = createConfigurationInternal(configName,clazz, parameters);                                                
                        
            container.addConfiguration(config.getConfiguration(), configName);
            descriptors.add(config.getDescriptor());
                        
            return config.getConfiguration();
        }
    }
    
    public List<ConfigurationDescriptor> getDescriptors() {
        return descriptors;
    }
    
    public ConfigurationDescriptor getDescriptor(String configName) {
        if(configName == null) {
            return null;
        }
        
        for(ConfigurationDescriptor descriptor : descriptors) {
            if(configName.equals(descriptor.getName())) {
                return descriptor;
            }
        }
        
        return null;
    }
    
    public Configuration getConfiguration(String configName) {
        return container.getConfiguration(configName);
    }
    
    protected abstract <T extends Configuration> ConfigurationWrapper<T> createConfigurationInternal(String configName, Class<T> clazz, 
            BuilderParameters parameters);

    /**
     * 检查配置库是否为空。
     * @return
     */
    public boolean isEmpty() {
        return container.isEmpty();
    }

    public boolean containsKey(String key) {
        return container.containsKey(key);
    }
    
    public boolean containsConfiguration(String configName) {
        return (container.getConfiguration(configName) != null);
    }
    
    public List<String> getConfigurationNameList() {
        return container.getConfigurationNameList();
    }
    
    public CombinedConfiguration getContainer() {
        return container;
    }

    public Object getProperty(String key) {
        return container.getProperty(key);
    }

    public Iterator<String> getKeys(String prefix) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();

        Iterator<String> iterator = container.getKeys();
        while(iterator.hasNext()) {
            keys.add(iterator.next());
        }
        return  keys;
    }

    public Properties getProperties(String key) {
        return container.getProperties(key);
    }

    public boolean getBoolean(String key) {
        return container.getBoolean(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return container.getBoolean(key, defaultValue);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return container.getBoolean(key, defaultValue);
    }

    public byte getByte(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public byte getByte(String key, byte defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Byte getByte(String key, Byte defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getDouble(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getDouble(String key, double defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Double getDouble(String key, Double defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getFloat(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float getFloat(String key, float defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Float getFloat(String key, Float defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getInt(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getInt(String key, int defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getInteger(String key, Integer defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getLong(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getLong(String key, long defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Long getLong(String key, Long defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public short getShort(String key) {
        return container.getShort(key);
    }

    public short getShort(String key, short defaultValue) {
        return container.getShort(key, defaultValue);
    }

    public Short getShort(String key, Short defaultValue) {
        return container.getShort(key, defaultValue);
    }

    public BigDecimal getBigDecimal(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BigInteger getBigInteger(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BigInteger getBigInteger(String key, BigInteger defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getString(String key) {
        return container.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return container.getString(key, defaultValue);
    }

    public String[] getStringArray(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Object> getList(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Object> getList(String key, List<Object> defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> T get(Class<T> cls, String key) {
        return container.get(cls, key);
    }

    public <T> T get(Class<T> cls, String key, T defaultValue) {
        return container.get(cls, key, defaultValue);
    }

    public Object getArray(Class<?> cls, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getArray(Class<?> cls, String key, Object defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> List<T> getList(Class<T> cls, String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> List<T> getList(Class<T> cls, String key, List<T> defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> Collection<T> getCollection(Class<T> cls, String key, Collection<T> target) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> Collection<T> getCollection(Class<T> cls, String key, Collection<T> target, Collection<T> defaultValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
