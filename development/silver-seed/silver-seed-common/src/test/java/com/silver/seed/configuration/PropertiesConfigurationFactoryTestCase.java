/**
 *
 */
package com.silver.seed.configuration;

import com.silver.wheel.io.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Liaojian
 *
 */
public class PropertiesConfigurationFactoryTestCase {

    private static final String PROPERTIES_RESOURCE_NAME = "properties/config.properties";

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        FileUtils.createResource(PROPERTIES_RESOURCE_NAME);

        Writer writer = new FileWriter(new File(getPropertiesFileURI()));
        writer.write("serverIP=10.140.15.124\n");
        writer.write("usereName=admin");
        writer.flush();
        writer.close();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
            FileUtils.deleteResource(PROPERTIES_RESOURCE_NAME, true);
    }

    /**
     * Test method for
     * {@link com.silver.seed.configuration.PropertiesConfigurationFactory#create(java.lang.String)}.
     */
    @Test
    public void testCreate() throws IOException, URISyntaxException, ConfigurationException {
        PropertiesConfigurationFactory factory = new PropertiesConfigurationFactory();
        PropertiesConfiguration config = factory.create(PROPERTIES_RESOURCE_NAME);
        String value = (String) config.getProperty("serverIP");
        assertEquals(value, "10.140.15.124");
        //在属性文件中添加新的属性
        FileUtils.appendToResource("\ndbName=ERP", PROPERTIES_RESOURCE_NAME);
        //增加新属性后，PropertiesConfiguration对象不能立即拿到新属性
        value = (String) config.getProperty("dbName");
        assertEquals(value, null);
        //执行refresh方法后可以拿到属性值
        config.refresh();
        value = (String) config.getProperty("dbName");
        assertEquals(value, "ERP");

    }

    private URI getPropertiesFileURI() throws URISyntaxException {
        return Thread.currentThread().getContextClassLoader().getResource(PROPERTIES_RESOURCE_NAME).toURI();
    }
}
