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
package org.panifex.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.panifex.module.api.Pagelet;
import org.panifex.module.api.PageletMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PageletTracker<TPagelet extends Pagelet<?, ?>> {

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Set<TPagelet> pagelets = new HashSet<>();
    private Set<PageletMapping> mappings = new HashSet<>();

    private BundleContext bundleContext;

    public final void addPagelet(ServiceReference<TPagelet> pageletReference) {
        TPagelet pagelet = bundleContext.getService(pageletReference);
        log.debug("Bind pagelet: {}", pagelet);
        pagelets.add(pagelet);
        onPageletAdded(pagelet);
    }

    public final void removePagelet(ServiceReference<TPagelet> pageletReference) {
        if (pageletReference != null) {
            TPagelet pagelet = bundleContext.getService(pageletReference);
            log.debug("Unbind pagelet: {}", pagelet);
            pagelets.remove(pagelet);
            onPageletRemoved(pagelet);
        }
    }

    public final void addPageletMapping(ServiceReference<PageletMapping> mappingReference) {
        PageletMapping mapping = bundleContext.getService(mappingReference);
        log.debug("Bind pagelet mapping: {}", mapping);
        mappings.add(mapping);
        onPageletMappingAdded(mapping);
    }

    public final void removePageletMapping(ServiceReference<PageletMapping> mappingReference) {
        if (mappingReference != null) {
            PageletMapping mapping = bundleContext.getService(mappingReference);
            log.debug("Unbind pagelet mapping: {}", mapping);
            mappings.remove(mapping);
            onPageletMappingRemoved(mapping);
        }
    }

    public final void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    protected final boolean containsPagelet(Pagelet<?, ?> pagelet) {
        return pagelets.contains(pagelet);
    }

    protected final List<TPagelet> getPagelets() {
        return new ArrayList<TPagelet>(pagelets);
    }

    protected final List<PageletMapping> getMappings() {
        return new ArrayList<PageletMapping>(mappings);
    }

    protected abstract void onPageletAdded(TPagelet pagelet);

    protected abstract void onPageletRemoved(TPagelet pagelet);

    protected abstract void onPageletMappingAdded(PageletMapping mapping);

    protected abstract void onPageletMappingRemoved(PageletMapping mapping);
}