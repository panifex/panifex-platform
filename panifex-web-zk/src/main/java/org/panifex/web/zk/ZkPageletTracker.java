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
package org.panifex.web.zk;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.panifex.module.api.pagelet.Pagelet;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.module.zk.api.ZkPagelet;
import org.panifex.web.controller.PageletTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.util.Configuration;

public class ZkPageletTracker extends PageletTracker<ZkPagelet>
        implements ServletContextListener {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ServletContext servletContext;
    private Set<String> registeredPagelet = new HashSet<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
        registerPagelets();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = null;
    }

    @Override
    protected void onPageletAdded(ZkPagelet pagelet) {
        registerPagelet(pagelet);
    }

    @Override
    protected void onPageletRemoved(ZkPagelet pagelet) {
        Configuration config = getConfiguration();
        if (config != null) {
            config.removeRichlet(pagelet.getClass().getCanonicalName());
        }
    }

    @Override
    protected void onPageletMappingAdded(PageletMapping mapping) {
        registerPageletMapping(mapping);
    }

    @Override
    protected void onPageletMappingRemoved(PageletMapping mapping) {
        Configuration config = getConfiguration();
        if (config != null) {
            // TODO remove pagelet mapping
        }
    }

    private Configuration getConfiguration() {
        ServletContext context = servletContext;
        if (context != null) {
            return WebManager.getWebApp(servletContext).getConfiguration();
        }
        return null;
    }

    private void registerPagelets() {
        for (Pagelet<?, ?> pagelet : getPagelets()) {
            registerPagelet(pagelet);
        }
    }

    private void registerPagelet(Pagelet<?, ?> pagelet) {
        log.debug("Register pagelet: {}", pagelet);
        ZkPagelet zkPagelet = (ZkPagelet) pagelet;
        Configuration config = getConfiguration();
        if (config != null) {
            String pageletName = zkPagelet.getClass().getCanonicalName();
            config.addRichlet(pageletName, zkPagelet);
            registeredPagelet.add(pageletName);
            registerPageletMappings(zkPagelet);
        }
    }

    private void registerPageletMappings(Pagelet<?, ?> pagelet) {
        List<PageletMapping> mappings = getMappings();
        for (PageletMapping mapping : mappings) {
            String pageletName = pagelet.getClass().getCanonicalName();
            if (pageletName.equals(mapping.getPageletName())) {
                registerPageletMapping(mapping);
            }
        }
    }

    private void registerPageletMapping(PageletMapping mapping) {
        log.debug("Register pagelet mapping: {}", mapping);
        Configuration config = getConfiguration();
        if (config != null) {
            String pageletName = mapping.getPageletName();
            if (registeredPagelet.contains(pageletName)) {
                for (String path : mapping.getUrlPatterns()) {
                    config.addRichletMapping(
                        pageletName,
                        path);
                 }
            }
        }
    }
}
