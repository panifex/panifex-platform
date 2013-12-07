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
package org.panifex.web.impl.sidebar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.module.api.sidebar.Sidebar;
import org.panifex.module.api.sidebar.SidebarItem;
import org.panifex.module.api.sidebar.SidebarNode;
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

    public final static String ID = "org.panifex.web.impl.sidebar.SidebarManager";

    @Inject
    @ReferenceList(availability = "optional", serviceInterface = Sidebar.class, referenceListeners = @ReferenceListener(ref = ID))
    private Sidebar sidebar;

    private Set<Sidebar> sidebars = new HashSet<>();
    private List<SidebarItem> sidebarItems = new ArrayList<>();
    
    @Bind
    public synchronized void bind(Sidebar sidebar) {
        log.debug("Bind sidebar: {}", sidebar);
        sidebars.add(sidebar);
        updateSidebarItems();
    }

    @Unbind
    public synchronized void unbind(Sidebar sidebar) {
        log.debug("Unbind sidebar: {}", sidebar);
        sidebars.remove(sidebar);
        updateSidebarItems();
    }

    private void updateSidebarItems() {
        TreeSet<SidebarItem> sidebarItems = new TreeSet<>();
        
        for (Sidebar sidebar : sidebars) {
            sidebarItems = mergeSidebarItems(sidebar.getSidebarItems(), sidebarItems);
        }
        
        this.sidebarItems = new ArrayList<>(sidebarItems);
    }
    
    /**
     * Merges a collection of new items with a collection of existed items and returns merged
     * collection
     * 
     * @param newItems A collection of new items
     * @param existedItems A collection of existed items
     * @return the merged collection
     */
    private TreeSet<SidebarItem> mergeSidebarItems(Collection<SidebarItem> newItems, Collection<SidebarItem> existedItems) {
        TreeSet<SidebarItem> mergedSet;
        if (existedItems == null) {
            mergedSet = new TreeSet<>();
        }
        
        if (existedItems instanceof TreeSet) {
            mergedSet = (TreeSet<SidebarItem>) existedItems;
        } else {
            mergedSet = new TreeSet<>(existedItems);
        }
        
        for (SidebarItem newItem : newItems) {
            mergedSet = putItemToSet(newItem, mergedSet);                    
        }
        
        return mergedSet;
    }
    
    private TreeSet<SidebarItem> putItemToSet(SidebarItem item, TreeSet<SidebarItem> set) {
        // find similar item
        SidebarItem existedItem = set.ceiling(item);
        
        if (existedItem != null) {
            // item has been found, check if they are equal
            if (existedItem.compareTo(item) == 0) {
                //  existed item and new item are equal, check if they are the same type
                if (existedItem instanceof SidebarNode && item instanceof SidebarNode) {
                    // merge children
                    SidebarNode existedNode = (SidebarNode) existedItem;
                    SidebarNode newNode = (SidebarNode) item;
                    
                    TreeSet<SidebarItem> merged = mergeSidebarItems(
                        newNode.getSidebarItems(), 
                        existedNode.getSidebarItems());
                    
                    existedNode.setSidebarItems(merged);
                } else {
                    log.warn("Sidebar item {} and {} are not the same type", existedItem, item);
                }
            } else {
                set.add(item.copy());
            }
        } else {
            // similar item hasn't been found, so just add item
            set.add(item.copy());
        }
        return set;
    }
    
    public List<SidebarItem> getSidebarItems() {
        return sidebarItems;
    }

    public static SidebarManager getManager() {
        SidebarManager sidebarManager;
        try {
            InitialContext ctx = new InitialContext();
            sidebarManager = (SidebarManager) ctx.lookup("blueprint:comp/" + SidebarManager.ID);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        };
        return sidebarManager;
    }
}
