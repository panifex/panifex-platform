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
package org.panifex.module.api;

/**
 * Default implementation of {@link PageletMapping}.
 */
public class DefaultPageletMapping implements PageletMapping {

    /**
     * Pagelet.
     */
    private final String pageletName;

    /**
     * Url patterns.
     */
    private String[] urlPatterns;

    /**
     * Constructs new pagelet mapping.
     *
     * @param pagelet a pagelet
     * @param urlPatterns an url patterns pagelet maps to
     */
    public DefaultPageletMapping(String pageletName, String[] urlPatterns) {
        if ((pageletName == null) || (urlPatterns == null)) {
            throw new IllegalArgumentException();
        }
        this.pageletName = pageletName;
        this.urlPatterns = urlPatterns;
    }

    /**
     * Constructs new pagelet mapping.
     *
     * @param pagelet a pagelet
     * @param urlPatterns an url patterns pagelet maps to
     */
    public DefaultPageletMapping(Pagelet<?, ?> pagelet, String[] urlPatterns) {
        this(pagelet.getClass().getCanonicalName(), urlPatterns);
    }

    @Override
    public final String getPageletName() {
        return pageletName;
    }

    @Override
    public final String[] getUrlPatterns() {
        return urlPatterns;
    }
}
