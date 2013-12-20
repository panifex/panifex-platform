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
package org.panifex.persistence.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import org.osgi.framework.BundleContext;

import liquibase.resource.ResourceAccessor;

public class BundleResourceAccessor implements ResourceAccessor {

    private BundleContext context;

    public BundleResourceAccessor(BundleContext context) {
        this.context = context;
    }

    @Override
    public InputStream getResourceAsStream(String file) throws IOException {
        URL resource = context.getBundle().getResource(file);
        return resource.openStream();
    }

    @Override
    public Enumeration<URL> getResources(String packageName) throws IOException {
        return context.getBundle().getResources(packageName);
    }

    @Override
    public ClassLoader toClassLoader() {
        return null;
    }

}
