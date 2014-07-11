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
import java.util.List;

import org.panifex.module.api.pagelet.Pagelet;
import org.panifex.module.api.pagelet.PageletMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PageletTracker<TPagelet extends Pagelet<?, ?>> {

    /**
     * Logger.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<TPagelet> pagelets = new ArrayList<>();
    private List<PageletMapping> pageletMappings = new ArrayList<>();

    public final void bindPagelet(TPagelet pagelet) {
        log.debug("Bind pagelet: {}", pagelet);
        if (pagelet != null) {
            pagelets.add(pagelet);
            onPageletBinded(pagelet);
        }
    }

    public final void unbindPagelet(TPagelet pagelet) {
        log.debug("Unbind pagelet: {}", pagelet);
        if (pagelet != null) {
            if (pagelets.remove(pagelet)) {
                onPageletUnbinded(pagelet);
            }
        }
    }

    public final void bindPageletMapping(PageletMapping pageletMapping) {
        log.debug("Bind pagelet mapping: {}", pageletMapping);
        if (pageletMapping != null) {
            pageletMappings.add(pageletMapping);
            onPageletMappingBinded(pageletMapping);
        }
    }

    public final void unbindPageletMapping(PageletMapping pageletMapping) {
        log.debug("Unbind pagelet mapping: {}", pageletMapping);
        if (pageletMapping != null) {
            if (pageletMappings.remove(pageletMapping)) {
                onPageletMappingUnbinded(pageletMapping);
            }
        }
    }

    protected final boolean containsPagelet(Pagelet<?, ?> pagelet) {
        return pagelets.contains(pagelet);
    }

    protected final List<TPagelet> getPagelets() {
        return pagelets;
    }

    protected final List<PageletMapping> getPageletMappings() {
        return pageletMappings;
    }

    protected abstract void onPageletBinded(TPagelet pagelet);

    protected abstract void onPageletUnbinded(TPagelet pagelet);

    protected abstract void onPageletMappingBinded(PageletMapping mapping);

    protected abstract void onPageletMappingUnbinded(PageletMapping mapping);
}