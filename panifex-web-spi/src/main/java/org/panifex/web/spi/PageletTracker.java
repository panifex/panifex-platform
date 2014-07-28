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
package org.panifex.web.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.apache.commons.lang3.StringUtils;
import org.panifex.module.api.pagelet.Pagelet;
import org.panifex.module.api.pagelet.PageletMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PageletTracker<TPagelet extends Pagelet<?>> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Map between registered pagelet names and active pagelets.
     */
    private Map<String, TPagelet> pagelets = new HashMap<>();

    /**
     * Collection of all registered pagelet mappings.
     */
    private List<PageletMapping> pageletMappings = new ArrayList<>();

    /**
     * Map between url patterns and active pagelets.
     */
    private Map<String, TPagelet> urlMappings = new HashMap<>();

    /**
     * Tracker lock.
     */
    private final ReentrantReadWriteLock trackerLock = new ReentrantReadWriteLock();

    protected final ReadLock readLock() {
        return trackerLock.readLock();
    }

    public final void bindPagelet(TPagelet pagelet) {
        log.debug("Bind pagelet: {}", pagelet);
        if (pagelet != null) {
            trackerLock.writeLock().lock();
            try {
                String pageletName = pagelet.getName();

                if (StringUtils.isEmpty(pageletName)) {
                    throw new IllegalArgumentException("Pagelet name cannot be null");
                }

                if (!pagelets.containsKey(pageletName)) {
                    pagelets.put(pageletName, pagelet);
                    onPageletBinded(pagelet);

                    for (PageletMapping mapping : pageletMappings) {
                        if (pageletName.equals(mapping.getPageletName())) {
                            addUrlMappings(pagelet, mapping.getUrlPatterns());
                        }
                    }
                } else {
                    String msg = new StringBuilder("Pagelet with name [").
                            append(pageletName).
                            append("] is already binded").
                            toString();
                    log.error(msg);
                    throw new IllegalArgumentException(msg);
                }
            } finally {
                trackerLock.writeLock().unlock();
            }
        }
    }

    public final void unbindPagelet(TPagelet pagelet) {
        log.debug("Unbind pagelet: {}", pagelet);
        if (pagelet != null) {
            trackerLock.writeLock().lock();
            try {
                String pageletName = pagelet.getName();
                if (containsPagelet(pageletName, pagelet)) {
                    pagelets.remove(pageletName);

                    for (PageletMapping mapping : pageletMappings) {
                        if (mapping.getPageletName().equals(pageletName)) {
                            removeUrlMappings(pagelet, mapping.getUrlPatterns());
                        }
                    }

                    onPageletUnbinded(pagelet);
                }
            } finally {
                trackerLock.writeLock().unlock();
            }
        }
    }

    public final void bindPageletMapping(PageletMapping pageletMapping) {
        log.debug("Bind pagelet mapping: {}", pageletMapping);
        if (pageletMapping != null) {
            trackerLock.writeLock().lock();
            try {
                String pageletName = pageletMapping.getPageletName();

                if (StringUtils.isEmpty(pageletName)) {
                    throw new IllegalArgumentException("pageletName cannot be null");
                }

                String[] urlPatterns = pageletMapping.getUrlPatterns();
                if (urlPatterns == null || urlPatterns.length == 0) {
                    throw new IllegalArgumentException("pagelet mapping should have at least one url pattern");
                }

                pageletMappings.add(pageletMapping);
                onPageletMappingBinded(pageletMapping);

                TPagelet pagelet = pagelets.get(pageletName);

                if (pagelet != null) {
                    addUrlMappings(pagelet, urlPatterns);
                }


            } finally {
                trackerLock.writeLock().unlock();
            }
        }
    }

    public final void unbindPageletMapping(PageletMapping pageletMapping) {
        log.debug("Unbind pagelet mapping: {}", pageletMapping);
        if (pageletMapping != null) {
            trackerLock.writeLock().lock();
            try {
                if (pageletMappings.remove(pageletMapping)) {

                    TPagelet pagelet = pagelets.get(pageletMapping.getPageletName());

                    if (pagelet != null) {
                        removeUrlMappings(pagelet, pageletMapping.getUrlPatterns());
                    }

                    onPageletMappingUnbinded(pageletMapping);
                }
            } finally {
                trackerLock.writeLock().unlock();
            }
        }
    }

    private void addUrlMappings(TPagelet pagelet, String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            if (!urlMappings.containsKey(urlPattern)) {
                urlMappings.put(urlPattern, pagelet);
                onUrlMappingAdded(urlPattern, pagelet);
            } else {
                String msg = new StringBuilder("Url pattern [").
                        append(urlPattern).
                        append("] has already mapped").
                        toString();
                log.error(msg);
                throw new IllegalArgumentException(msg);
            }
        }
    }

    private void removeUrlMappings(TPagelet pagelet, String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            TPagelet activePagelet = urlMappings.get(urlPattern);
            if (pagelet.equals(activePagelet)) {
                urlMappings.remove(urlPattern);
                onUrlMappingRemoved(urlPattern, activePagelet);
            }
        }
    }

    private final boolean containsPagelet(String pageletName, TPagelet pagelet) {
        TPagelet activePagelet = pagelets.get(pageletName);
        return activePagelet != null &&
                activePagelet.equals(pagelet);
    }

    protected final Collection<TPagelet> getPagelets() {
        return pagelets.values();
    }

    protected final List<PageletMapping> getPageletMappings() {
        return pageletMappings;
    }

    public void onUrlMappingAdded(String urlPattern, TPagelet pagelet) {
        // do nothing
    }

    public void onUrlMappingRemoved(String urlPattern, TPagelet pagelet) {
        // do nothing
    }

    public void onPageletBinded(TPagelet pagelet) {
        // do nothing
    }

    public void onPageletUnbinded(TPagelet pagelet) {
        // do nothing
    }

    public void onPageletMappingBinded(PageletMapping mapping) {
        // do nothing
    }

    public void onPageletMappingUnbinded(PageletMapping mapping) {
        // do nothing
    }

    public final TPagelet matchPathToPagelet(String path) {
        TPagelet matched = null;
        String servletPath = path;

        while (matched == null && !"".equals(servletPath)) {
            // Match the asterisks first that comes just after the current
            // servlet path, so that it satisfies the longest path req
            if (servletPath.endsWith("/")) {
                matched = urlMappings.get(servletPath + "*");
            } else {
                matched = urlMappings.get(servletPath + "/*");
            }

            // try to match the exact resource if the above fails
            if (matched == null) {
                matched = urlMappings.get(servletPath);
            }

            // now try to match the url backwards one directory at a time
            if (matched == null) {
                String lastPathSegment = servletPath.substring(servletPath
                        .lastIndexOf("/") + 1);
                servletPath = servletPath.substring(0,
                        servletPath.lastIndexOf("/"));
                // case 1: the servlet path is /
                if ("".equals(servletPath) && "".equals(lastPathSegment)) {
                    break;
                } else if ("".equals(lastPathSegment)) {
                // case 2 the servlet path ends with /
                    matched = urlMappings.get(servletPath + "/*");
                    continue;
                } else if (lastPathSegment.contains(".")) {
                // case 3 the last path segment has a extension that needs to be
                // matched
                    String extension = lastPathSegment
                            .substring(lastPathSegment.lastIndexOf("."));
                    if (extension.length() > 1) {
                        // case 3.5 refer to second bulleted point of heading
                        // Specification of Mappings
                        // in servlet specification
                        // PATCH - do not go global too early. Next 3 lines
                        // modified.
                        // matched = urlPatternsMap.get("*" + extension);
                        if (matched == null) {
                            matched = urlMappings.get((""
                                    .equals(servletPath) ? "*" : servletPath
                                    + "/*")
                                    + extension);
                        }
                    }
                }
                // case 4 search for the wild cards at the end of servlet path
                // of the next iteration
                else {
                    if (servletPath.endsWith("/")) {
                        matched = urlMappings.get(servletPath + "*");
                    } else {
                        matched = urlMappings.get(servletPath + "/*");
                    }
                }

                // case 5 if all the above fails look for the actual mapping
                if (matched == null) {
                    matched = urlMappings.get(servletPath);
                }

                // case 6 the servlet path has / followed by context name, this
                // case is
                // selected at the end of the directory, when none of the them
                // matches.
                // So we try to match to root.
                if (matched == null && "".equals(servletPath)
                        && !"".equals(lastPathSegment)) {
                    matched = urlMappings.get("/");
                }
            }
        }
        return matched;
    }
}