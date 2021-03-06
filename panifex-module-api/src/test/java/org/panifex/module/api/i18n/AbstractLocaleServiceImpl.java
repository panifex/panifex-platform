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
package org.panifex.module.api.i18n;

import java.util.Locale;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.panifex.module.api.i18n.AbstractLocaleService;

/**
 * A {@link AbstractLocaleService} implementation for using it
 * in unit tests. 
 */
final class AbstractLocaleServiceImpl extends AbstractLocaleService {

    public AbstractLocaleServiceImpl(Locale locale) {
        super(locale);
    }

    public AbstractLocaleServiceImpl(Locale locale, Set<String> resources) {
        super(locale, resources);
    }
    
    @Override
    public void setBundleContext(BundleContext bundleContext) {
        setBundleContext0(bundleContext);
    }

}
