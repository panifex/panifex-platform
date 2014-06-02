/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.web.zk.itest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WaitCondition {
    private static final long WAIT_TIMEOUT_MILLIS = 10000;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private String description;

    protected WaitCondition(String description) {
        this.description = description;
    }

    protected String getDescription() {
        return description;
    }

    protected abstract boolean isFulfilled() throws Exception;

    public void waitForCondition() {
        //CHECKSTYLE:OFF
        long startTime = System.currentTimeMillis();
        try {
            while (!isFulfilled() && (System.currentTimeMillis() < (startTime + WAIT_TIMEOUT_MILLIS))) {
                Thread.sleep(100);
            }
            if (!isFulfilled()) {
                LOG.warn("Waited for {} for {} ms but condition is still not fulfilled", getDescription(), System.currentTimeMillis() - startTime);
            } else {
                LOG.info("Successfully waited for {} for {} ms", getDescription(), System.currentTimeMillis() - startTime);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error waiting for " + getDescription(), e);
        }
        //CHECKSTYLE:ON
    }
}
