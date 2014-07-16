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
package org.panifex.web.vaadin;

import java.util.List;

import org.apache.shiro.util.PatternMatcher;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.module.vaadin.api.VaadinPagelet;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class PageletAwareUI extends UI {

    private final List<PageletMapping> pageletMappings;
    private final List<VaadinPagelet> pagelets;
    private final PatternMatcher patternMatcher;

    public PageletAwareUI(
            List<PageletMapping> pageletMappings,
            List<VaadinPagelet> pagelets,
            PatternMatcher patternMatcher) {
        if (pageletMappings == null) {
            throw new IllegalArgumentException("pageletMappings cannot be null");
        }
        if (pagelets == null) {
            throw new IllegalArgumentException("pagelets cannot be null");
        }
        if (patternMatcher == null) {
            throw new IllegalArgumentException("pathMatcher cannot be null");
        }
        this.pageletMappings = pageletMappings;
        this.pagelets = pagelets;
        this.patternMatcher = patternMatcher;
    }

    @Override
    protected void init(VaadinRequest request) {
        for (PageletMapping mapping : pageletMappings) {
            String[] urlPatterns = mapping.getUrlPatterns();
            for (String urlPattern : urlPatterns) {
                if (patternMatcher.matches(urlPattern, request.getPathInfo())) {
                    for (VaadinPagelet pagelet : pagelets) {
                        if (mapping.getPageletName().equals(pagelet.getName())) {
                            pagelet.service(request);
                            return;
                        }
                    }
                }
            }
        }
    }

}
