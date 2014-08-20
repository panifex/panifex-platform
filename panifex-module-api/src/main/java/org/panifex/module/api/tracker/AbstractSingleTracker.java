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
package org.panifex.module.api.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSingleTracker<Service> implements SingleTracker<Service> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Service service;

    @Override
    public void bind(Service service) {
        log.debug("Bind service: {}", service);
        this.service = service;
    }

    @Override
    public void unbind(Service service) {
        log.debug("Unbind service: {}", service);
        this.service = null;
    }

    @Override
    public Service service() {
        return service;
    }

}
