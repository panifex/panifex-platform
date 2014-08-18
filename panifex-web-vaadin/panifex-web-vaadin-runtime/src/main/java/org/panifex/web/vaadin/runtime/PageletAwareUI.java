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
package org.panifex.web.vaadin.runtime;

import org.panifex.module.vaadin.api.VaadinPagelet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class PageletAwareUI extends UI {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VaadinPageletTracker pageletTracker;

    public PageletAwareUI(VaadinPageletTracker pageletTracker) {
        if (pageletTracker == null) {
            throw new IllegalArgumentException("pageletTracker cannot be null");
        }
        this.pageletTracker = pageletTracker;
    }

    @Override
    protected void init(VaadinRequest request) {
        VaadinPagelet pagelet = pageletTracker.matchPathToPagelet(request.getPathInfo());
        if (pagelet != null) {
            try {
                pagelet.service(request);
            } catch (Exception e) {
                log.error("Unable to service request", e);
                throw new RuntimeException(e);
            }
        }
    }
}
