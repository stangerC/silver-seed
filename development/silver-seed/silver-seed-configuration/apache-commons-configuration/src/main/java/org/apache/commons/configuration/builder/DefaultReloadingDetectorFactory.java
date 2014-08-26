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
package org.apache.commons.configuration.builder;

import org.apache.commons.configuration.ex.ConfigurationException;
import org.apache.commons.configuration.io.FileHandler;
import org.apache.commons.configuration.reloading.FileHandlerReloadingDetector;
import org.apache.commons.configuration.reloading.ReloadingDetector;

/**
 * <p>
 * A default implementation of the {@code ReloadingDetectorFactory} interface.
 * </p>
 * <p>
 * This factory creates objects of type {@link FileHandlerReloadingDetector}.
 * Instances have no state and can be shared between multiple builders.
 * </p>
 *
 * @version $Id: DefaultReloadingDetectorFactory.java 1554634 2014-01-01 16:41:48Z oheger $
 * @since 2.0
 */
public class DefaultReloadingDetectorFactory implements
        ReloadingDetectorFactory
{
    public ReloadingDetector createReloadingDetector(FileHandler handler,
            FileBasedBuilderParametersImpl params)
            throws ConfigurationException
    {
        Long refreshDelay = params.getReloadingRefreshDelay();
        return (refreshDelay != null) ? new FileHandlerReloadingDetector(
                handler, refreshDelay) : new FileHandlerReloadingDetector(
                handler);
    }
}
