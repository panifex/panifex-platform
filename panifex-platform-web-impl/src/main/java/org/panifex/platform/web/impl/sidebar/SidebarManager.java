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
package org.panifex.platform.web.impl.sidebar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.platform.module.api.sidebar.Sidebar;
import org.panifex.platform.module.api.sidebar.SidebarItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sidebar manager builds a sidebar menu based on registered sidebar services.
 * 
 * @see Sidebar
 */
@Bean(id = SidebarManager.ID)
@ReferenceListener
public class SidebarManager {

    private Logger log = LoggerFactory.getLogger(SidebarManager.class);

    public final static String ID = "org.panifex.platform.web.impl.sidebar.SidebarManager";

    @Inject
    @ReferenceList(availability = "optional", serviceInterface = Sidebar.class, referenceListeners = @ReferenceListener(ref = ID))
    private Sidebar sidebar;

    private Set<Sidebar> sidebarItems = new HashSet<>();

    @Bind
    public void bind(Sidebar sidebar) {
        log.debug("Bind sidebar: {}", sidebar);
        sidebarItems.add(sidebar);
    }

    @Unbind
    public void unbind(Sidebar sidebar) {
        log.debug("Unbind sidebar: {}", sidebar);
        sidebarItems.remove(sidebar);
    }

    public List<SidebarItem> getSidebarItems() {

        List<SidebarItem> list = new ArrayList<>();

        return list;
    }

}
