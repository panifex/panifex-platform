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
package org.panifex.web.shiro.mgt;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.panifex.module.api.security.SecFilter;
import org.panifex.module.api.security.SecFilterMapping;

/**
 * The default {@link SecFilterMappingMap} implementation.
 *
 * @since 1.0
 */
public class SecFilterMappingMapImpl implements SecFilterMappingMap {

    /**
     * The map of {@link SecFilterMapping}'s. It contains key: the {@link SecFilter}'s name
     * and value: the mapped collection of associated {@SecFilterMapping}s and the
     * {@link SecFilter}'s URL.
     */
    private Map<String, Map<String, SecFilterMappingList>> mappings = new LinkedHashMap<>();

    /**
     * Adds the new {@link SecFilterMapping} to the collection.
     *
     * @param filterMapping
     *      the {@link SecFilterMapping} to be added to the collection
     */
    @Override
    public void add(SecFilterMapping filterMapping) {
        String filterName = filterMapping.getFilterName();
        String url = filterMapping.getUrl();

        SecFilterMappingList filterList = getOrCreateFilterMappingList(filterName, url);
        filterList.add(filterMapping);
    }

    /**
     * Removes the {@link SecFilterMapping} from the collection.
     *
     * @param filterMapping
     *      the {@link SecFilterMapping} to be removed from the collection
     */
    @Override
    public void remove(SecFilterMapping filterMapping) {
        String filterName = filterMapping.getFilterName();
        String url = filterMapping.getUrl();

        SecFilterMappingList filterList = getFilterMappingList(filterName, url);

        filterList.remove(filterMapping);

        // clean maps if they are empty
        if (filterList.isEmpty()) {
            Map<String, SecFilterMappingList> urlFilterMap = getUrlFilterMappingMap(filterName);
            if (urlFilterMap != null) {
                urlFilterMap.remove(url);
            }
            if (urlFilterMap == null || urlFilterMap.isEmpty()) {
                mappings.remove(filterName);
            }
        }
    }

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
    @Override
    public boolean isFilterMapped(String filterName, String url) {
        SecFilterMappingList filterList = getFilterMappingList(filterName, url);
        return filterList != null;
    }

    /**
     * Returns the set of the URLs that is the filter mapped to.
     *
     * @param filterName
     *      the filter's name
     * @return
     */
    @Override
    public Set<String> getMappedUrls(String filterName) {
        Map<String, SecFilterMappingList> urlFilterMap = getUrlFilterMappingMap(filterName);
        if (urlFilterMap != null) {
            return urlFilterMap.keySet();
        }
        return null;
    }

    private Map<String, SecFilterMappingList> getUrlFilterMappingMap(String filterName) {
        return mappings.get(filterName);
    }

    private Map<String, SecFilterMappingList> getOrCreateUrlFilterMappingMap(String filterName, String url) {
        Map<String, SecFilterMappingList> filterUrlMap = mappings.get(filterName);
        if (filterUrlMap == null) {
            filterUrlMap = new LinkedHashMap<>();
            mappings.put(filterName, filterUrlMap);
        }
        return filterUrlMap;
    }

    private SecFilterMappingList getFilterMappingList(String filterName, String url) {
        Map<String, SecFilterMappingList> urlFilterMap = getUrlFilterMappingMap(filterName);

        if (urlFilterMap != null) {
            return urlFilterMap.get(url);
        }
        return null;
    }

    private SecFilterMappingList getOrCreateFilterMappingList(String filterName, String url) {
        Map<String, SecFilterMappingList> urlFilterMap = getOrCreateUrlFilterMappingMap(filterName, url);

        SecFilterMappingList filterList;
        if (urlFilterMap.containsKey(url)) {
            filterList = urlFilterMap.get(url);
        } else {
            filterList = new SecFilterMappingList();
            urlFilterMap.put(url, filterList);
        }
        return filterList;
    }
}
