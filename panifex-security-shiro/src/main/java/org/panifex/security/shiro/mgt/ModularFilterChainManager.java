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

import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.panifex.module.api.security.SecFilter;
import org.panifex.module.api.security.SecFilterMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The modular {@link FilterChainManager} implementation maintaining a map of {@link Filter
 * Filter} instances (key: filter name, value: Filter) as well as a map of {@link NamedFilterList
 * NamedFilterList}s created from these {@code Filter}s (key: filter chain name, value:
 * NamedFilterList). The {@code NamedFilterList} is essentially a {@link FilterChain} that
 * also has a name property by which it can be looked up.
 *
 * @since 1.0
 */
public class ModularFilterChainManager extends DefaultFilterChainManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * The collection of active {@link SecFilterMapping}s. The mapping can be resolved
     * only if the associated {@link SecFilter} is already active. Otherwise, the mapping
     * waits for {@link SecFilter} to be binded.
     */
    private final SecFilterMappingMap filterMappings;

    private final Object LOCK = new Object();

    /**
     * Initializes a new filter chain manager instance.
     *
     * @param filterMappings
     *      the {@link SecFilterMappingMap} implementation
     *
     */
    public ModularFilterChainManager(SecFilterMappingMap filterMappings) {
        this.filterMappings = filterMappings;
    }

    /**
     * Binds the {@link SecFilter} to the Shiro's environment.
     *
     * @param filter
     *      the {@link SecFilter} to be binded
     * @since 1.0
     */
    public void bindFilter(SecFilter filter) {
        log.debug("Bind sec filter: {}", filter);
        if (isFilterValid(filter)) {
            synchronized (LOCK) {
                String filterName = filter.getFilterName();

                addFilter(filterName, filter);

                Set<String> urls = filterMappings.getMappedUrls(filterName);
                if (urls != null) {
                    for (String url : urls) {
                        addToChain(url, filterName);
                    }
                }
            }
        }
    }

    /**
     * Unbinds the {@link SecFilter} from the Shiro's environment.
     *
     * @param filter
     *      the {@link SecFilter} to be unbinded
     * @since 1.0
     */
    public void unbindFilter(SecFilter filter) {
        log.debug("Unbind sec filter: {}", filter);
        if (isFilterValid(filter)) {
            synchronized (LOCK) {
                String filterName = filter.getFilterName();

                Set<String> urls = filterMappings.getMappedUrls(filterName);
                if (urls != null) {
                    for (String url : urls) {
                        removeFilterFromFilterChains(filterName, url);
                    }
                }

                Map<String, Filter> filters = getFilters();
                filters.remove(filter);
            }
        }
    }

    /**
     * Binds the {@link SecFilterMapping} to the Shiro's environment.
     *
     * @param filterMapping
     *      the {@link SecFilterMapping} to be binded
     * @since 1.0
     */
    public void bindFilterMapping(SecFilterMapping filterMapping) {
        log.debug("Bind sec filter mapping: {}", filterMapping);
        if (isFilterMappingValid(filterMapping)) {
            synchronized (LOCK) {
                String filterName = filterMapping.getFilterName();
                String url = filterMapping.getUrl();

                Filter filter = getFilter(filterName);

                boolean isFilterAlreadyMapped;

                if (filter != null) {
                    isFilterAlreadyMapped = filterMappings.isFilterMapped(filterName, url);
                } else {
                    isFilterAlreadyMapped = false;
                }

                filterMappings.add(filterMapping);

                if (filter != null && !isFilterAlreadyMapped) {
                    addToChain(url, filterName);
                }
            }
        }
    }

    /**
     * Unbinds the {@link SecFilterMapping} from the Shiro's environment.
     *
     * @param filterMapping
     *      the {@link SecFilterMapping} to be unbinded
     * @since 1.0
     */
    public void unbindFilterMapping(SecFilterMapping filterMapping) {
        log.debug("Unbind sec filter mapping: {}", filterMapping);
        if (isFilterMappingValid(filterMapping)) {
            synchronized (LOCK) {
                String filterName = filterMapping.getFilterName();
                String url = filterMapping.getUrl();

                filterMappings.remove(filterMapping);

                Filter filter = getFilter(filterName);
                if (filter != null) {
                    boolean isFilterMapped = filterMappings.isFilterMapped(filterName, url);

                    if (!isFilterMapped) {
                        removeFilterFromFilterChains(filterName, url);
                    }
                }
            }
        }
    }

    private boolean isFilterValid(SecFilter filter) {
        if (filter != null) {
            if (filter.getFilterName() == null) {
                throw new IllegalArgumentException("filterName cannot be null");
            }
            return true;
        }
        log.warn("Sec filter is null");
        return false;
    }

    private boolean isFilterMappingValid(SecFilterMapping filterMapping) {
        if (filterMapping != null) {
            if (filterMapping.getFilterName() == null) {
                throw new IllegalArgumentException("filterName cannot be null");
            }
            if (filterMapping.getUrl() == null) {
                throw new IllegalArgumentException("url cannot be null");
            }
            return true;
        }
        log.warn("Sec filter mapping is null");
        return false;
    }

    private void removeFilterFromFilterChains(String filterName, String url) {
        Map<String, NamedFilterList> filterChains = getFilterChains();
        NamedFilterList filterList = filterChains.get(url);
        if (filterList != null) {
            Filter filter = getFilter(filterName);
            filterList.remove(filter);
            if (filterList.isEmpty()) {
                filterChains.remove(url);
            }
        }
    }
}
