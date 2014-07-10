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
package org.panifex.security.shiro.mgt;

import java.util.Set;

import org.panifex.module.api.security.SecFilterMapping;

/**
 * The collection of the {@link SecFilterMapping}s.
 * <p>
 * It enables finding the mappings according to the filter's name and to get the set of
 * URLs in which the filter is mapped to.
 * <p>
 * <strong>Note that this implementation is not synchronized.</strong>
 *
 * @since 1.0
 */
interface SecFilterMappingMap {

    /**
     * Adds the new {@link SecFilterMapping} to the collection.
     *
     * @param filterMapping
     *      the {@link SecFilterMapping} to be added to the collection
     */
    void add(SecFilterMapping filterMapping);

    /**
     * Removes the {@link SecFilterMapping} from the collection.
     *
     * @param filterMapping
     *      the {@link SecFilterMapping} to be removed from the collection
     */
    void remove(SecFilterMapping filterMapping);

    /**
     * Returns true if the filter is mapped to the specified URL pattern.
     *
     * @param filterName
     *      the filter's name
     * @param url
     *      the URL pattern
     * @return
     *      true if the filter is mapped to the specified URL pattern
     */
    boolean isFilterMapped(String filterName, String url);

    /**
     * Returns the set of the URLs that is the filter mapped to.
     *
     * @param filterName
     *      the filter's name
     * @return
     */
    Set<String> getMappedUrls(String filterName);
}
