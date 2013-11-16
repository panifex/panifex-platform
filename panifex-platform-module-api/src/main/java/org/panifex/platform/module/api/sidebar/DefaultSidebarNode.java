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
package org.panifex.platform.module.api.sidebar;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DefaultSidebarNode extends AbstractSidebarItem implements SidebarNode {

    private Collection<SidebarItem> sidebarItems = new ArrayList<>();
    private String badgeText;
    
    @Override
    public Collection<SidebarItem> getSidebarItems() {
        return sidebarItems;
    }

    public void setSidebarItems(Collection<SidebarItem> sidebarItems) {
        this.sidebarItems = sidebarItems;
    }
    
    @Override
    public String getBadgeText() {
        return badgeText;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) { 
            return false; 
        }
        if (other == this) { 
            return true; 
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        DefaultSidebarNode obj = (DefaultSidebarNode) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(sidebarItems, obj.sidebarItems)
                .append(badgeText, obj.badgeText)
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(339, 901)
                .appendSuper(super.hashCode())
                .append(sidebarItems)
                .append(badgeText)
                .toHashCode();
    }

    @Override
    public DefaultSidebarNode copy() {
        DefaultSidebarNode cloned = new DefaultSidebarNode();
        super.copyValues(this, cloned);
        
        for (SidebarItem item : sidebarItems) {
            cloned.sidebarItems.add(item.copy());
        }
        
        return null;
    }
   
}
