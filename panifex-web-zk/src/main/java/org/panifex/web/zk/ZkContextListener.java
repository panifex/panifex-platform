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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.util.Configuration;

public class ZkContextListener implements ServletContextListener {

    private final ZkRichletDispatcher richletDispatcher;
    private ServletContext servletContext;

    public ZkContextListener(ZkRichletDispatcher richletDispatcher) {
        if (richletDispatcher == null) {
            throw new IllegalArgumentException("richletDispatcher cannot be null");
        }
        this.richletDispatcher = richletDispatcher;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        Configuration config = WebManager.getWebApp(servletContext).getConfiguration();
        if (config != null) {
            this.servletContext = servletContext;
            String richletName = richletDispatcher.getName();
            config.addRichlet(richletName, richletDispatcher);
            config.addRichletMapping(richletName, "/*");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sce.getServletContext().equals(servletContext)) {
            servletContext = null;
        }
    }
}
