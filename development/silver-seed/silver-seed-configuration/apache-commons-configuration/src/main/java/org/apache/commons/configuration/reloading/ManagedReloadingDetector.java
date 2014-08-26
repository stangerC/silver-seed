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

package org.apache.commons.configuration.reloading;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A strategy to reload configuration based on management requests. Designed for
 * JMX management.
 *
 * @author Nicolas De loof
 * @version $Id: ManagedReloadingDetector.java 1479353 2013-05-05 17:37:31Z oheger $
 */
public class ManagedReloadingDetector implements ReloadingDetector,
        ManagedReloadingDetectorMBean
{
    /** The logger. */
    private Log log = LogFactory.getLog(ManagedReloadingDetector.class);

    /** A flag whether a reload is required. */
    private volatile boolean reloadingRequired;

    /**
     * {@inheritDoc} This implementation resets the internal flag indicating
     * that a reload should be performed.
     */
    public void reloadingPerformed()
    {
        reloadingRequired = false;
    }

    /**
     * Checks whether reloading is required. This implementation checks whether
     * the {@code refresh()} method has been invoked.
     *
     * @return a flag whether reloading is required
     */
    public boolean isReloadingRequired()
    {
        return reloadingRequired;
    }

    /**
     * Tells this strategy that the monitored configuration file should be
     * refreshed. This method will typically be called from outside (through an
     * exposed MBean) on behalf of an administrator.
     *
     * @see org.apache.commons.configuration.reloading.ManagedReloadingDetectorMBean#refresh()
     */
    public void refresh()
    {
        log.info("Reloading configuration.");
        reloadingRequired = true;
    }
}