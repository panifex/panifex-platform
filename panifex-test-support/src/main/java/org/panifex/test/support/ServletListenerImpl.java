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
package org.panifex.test.support;

import org.ops4j.pax.web.service.spi.ServletEvent;
import org.ops4j.pax.web.service.spi.ServletListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletListenerImpl implements ServletListener {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private boolean event;

    private String servletName;

    public ServletListenerImpl(String servletName) {
        this.servletName = servletName;
    }

    public ServletListenerImpl() {
    }

    @Override
    public void servletEvent(ServletEvent servletEvent) {
        LOG.info("Got event: " + servletEvent);
        boolean checkServletName = servletName != null ? true : false;

        boolean servletMatch = true;
        if (checkServletName) {
            servletMatch = servletName.equalsIgnoreCase(servletEvent.getServletName());
        }
        if ((servletEvent.getType() == ServletEvent.DEPLOYED) && servletMatch) {
            LOG.info("servletEventMatched with checkServletName?{}", checkServletName);
            event = true;
        } else if (servletEvent.getType() == ServletEvent.UNDEPLOYED) {
            event = false;
        }
    }

    public boolean gotEvent() {
        return event;
    }
}
