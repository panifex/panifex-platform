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
package org.panifex.web.impl.menu;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.panifex.module.api.menu.AppMenuService;
import org.panifex.module.api.menu.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.TreeNode;

/**
 * This class contains a reference to an active
 * {@link AppMenuServiceImpl AppMenuServiceImpl}.
 * <p>
 *
 */
public final class AppMenuServiceHolder implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1406210037353560969L;

    public static final String ID = "org.panifex.web.impl.menu.AppMenuManager";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static AppMenuService service;

    public void registerAppMenuService(Serializable service, Map<String, String> props) {
        log.debug("Bind AppMenuService: {}", service);
        AppMenuServiceHolder.service = (AppMenuService) service;
    }

    public void unregisterAppMenuService(Serializable service, Map<String, String> props) {
        log.debug("Unbind AppMenuService: {}", service);
        AppMenuServiceHolder.service = null;
    }

    public static List<TreeNode<MenuItem>> getMenuItems() {
        //return service.getMenuItems();
        return null;
    }
}
