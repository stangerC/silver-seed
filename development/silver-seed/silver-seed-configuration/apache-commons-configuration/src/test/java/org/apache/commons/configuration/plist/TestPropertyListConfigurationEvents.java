/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.configuration.plist;

import java.io.File;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationAssert;
import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.ex.ConfigurationRuntimeException;
import org.apache.commons.configuration.io.FileHandler;

/**
 * Test class for the events generated by PropertyListConfiguration.
 *
 * @version $Id: TestPropertyListConfigurationEvents.java 1554634 2014-01-01 16:41:48Z oheger $
 */
public class TestPropertyListConfigurationEvents extends
        AbstractTestPListEvents
{
    /** Constant for the test file that will be loaded. */
    private static final File TEST_FILE = ConfigurationAssert.getTestFile("test.plist");

    @Override
    protected AbstractConfiguration createConfiguration()
    {
        try
        {
            PropertyListConfiguration c = new PropertyListConfiguration();
            new FileHandler(c).load(TEST_FILE);
            return c;
        }
        catch (ConfigurationException cex)
        {
            throw new ConfigurationRuntimeException(cex);
        }
    }

}
