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
package org.panifex.module.api.locale;

import java.net.URL;
import java.util.Locale;
import java.util.Set;

/**
 * A LocaleService is used to locate extra resources for 
 * {@link org.zkoss.util.resource.Labels Labels}. 
 * 
 * @see {@link org.zkoss.util.resource.Labels}
 */
public interface LocaleService {

    /**
     * Returns the {@link java.util.Locale Locale} of
     * extra resources.
     */
    Locale supportedLocale();

    /** 
     * Returns a collection of URLs containing the labels for the specified 
     * locale or null if not available.
     * <p>
     * It must be thread-safe.
     */
    Set<URL> locates();
}
