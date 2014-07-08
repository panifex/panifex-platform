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
package org.panifex.module.api.security;

import org.apache.shiro.web.filter.mgt.DefaultFilter;

/**
 * The default {@link SecFilterMapping} implementation.
 *
 * @since 1.0
 */
public class DefaultSecFilterMapping implements SecFilterMapping {

    /**
     * The URL pattern to {@link SecFilter} is mapped to
     */
    private final String url;

    /**
     * The {@link SecFilter}'s name to be mapped to the specified URL pattern
     */
    private final String filterName;

    /**
     * Initializes a newly creted {@link SecFilterMapping} object which associates the
     * default Shiro's filter to the specified URL pattern.
     *
     * @param url
     *      the URL pattern which the default Shiro's filter is mapped to
     * @param defaultFilter
     *      the default Shiro's filter instance to be mapped
     */
    public DefaultSecFilterMapping(String url, DefaultFilter defaultFilter) {
        this(url, defaultFilter.toString());
    }

    /**
     * Initializes a newly created {@link SecFilterMapping} object which associates
     * the {@link SecFilter} to the specified URL pattern.
     * <p/>
     * The filter is mapped to the {@link SecFilter}'s canonical class name.
     *
     * @param url
     *      the URL pattern which the {@link SecFilter} is mapped to
     * @param filter
     *      the {@link SecFilter} to be mapped to the specified URL pattern
     * @since 1.0
     */
    public DefaultSecFilterMapping(String url, SecFilter filter) {
        this (url, filter.getFilterName());
    }

    /**
     * Initializes a newly created {@link SecFilterMapping} object which associates
     * the {@link SecFilter}'s name to the specified URL pattern.
     *
     * @param url
     *      the URL pattern to the {@link SecFilter}'s name is mapped to
     * @param filterName
     *      the {@link SecFilter}'s name to be mapped to the specified URL pattern
     * @since 1.0
     */
    public DefaultSecFilterMapping(String url, String filterName) {
        this.url = url;
        this.filterName = filterName;
    }

    /**
     * Returns the URL pattern to {@link SecFilter} is mapped to.
     *
     * @since 1.0
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Returns the {@link SecFilter}'s name that is mapped to the specified URL
     *
     * @since 1.0
     */
    @Override
    public String getFilterName() {
        return filterName;
    }

}
