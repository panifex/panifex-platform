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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMultiTracker<Service> implements MultiTracker<Service> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Service> services = new ArrayList<>();

    @Override
    public void bind(Service service) {
        log.debug("Bind service: {}", service);
        services.add(service);
    }

    @Override
    public void unbind(Service service) {
        log.debug("Unbind service: {}", service);
        services.remove(service);
    }

    @Override
    public List<Service> services() {
        return services;
    }
}
