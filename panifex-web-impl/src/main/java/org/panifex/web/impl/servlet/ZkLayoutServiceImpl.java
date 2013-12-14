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
package org.panifex.web.impl.servlet;

import java.util.Map;

import org.apache.aries.blueprint.annotation.Bean;
import org.zkoss.zk.ui.Richlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.util.Configuration;

@Bean(id = ZkLayoutServiceImpl.ID)
public class ZkLayoutServiceImpl extends DHtmlLayoutServlet implements ZkLayoutService {

    public final static String ID = "org.panifex.web.impl.servlet.ZkLayoutServlet";

    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = 4082554202918040779L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Configuration getConfiguration() {
        return WebManager.getWebManager(getServletContext()).getWebApp().getConfiguration();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object addRichlet(Class<? extends Richlet> richlet, String path) {
        return addRichlet(richlet, path, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object addRichlet(Class<? extends Richlet> richlet, String path,
            Map<String, String> params) {
        // get the configuration
        Configuration config = getConfiguration();

        // register Richlet
        String richletName = richlet.getName();
        Object previousRichlet = config.addRichlet(richletName, richletName, params);
        config.addRichletMapping(richletName, path);

        return previousRichlet;
    }
}
