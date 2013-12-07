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
package org.panifex.module.api.sidebar;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class AbstractSidebarItem implements SidebarItem {

    private String label;
    private String iconSclass;
    private int priority;
    
    protected AbstractSidebarItem(String label, int priority) {
        this.label = label;
        this.priority = priority;
    }
    
    protected AbstractSidebarItem(AbstractSidebarItem oldItem) {
        // just copy values because immutability
        this.label = oldItem.label;
        this.iconSclass = oldItem.iconSclass;
        this.priority = oldItem.priority;
    }
    
    @Override
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getIconSclass() {
        return iconSclass;
    }
    
    public void setIconSclass(String iconSclass) {
        this.iconSclass = iconSclass;
    }

    @Override
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(SidebarItem other) {
        AbstractSidebarItem object = (AbstractSidebarItem) other;
        return new CompareToBuilder()
          .append(this.priority, object.priority)
          .append(this.label, object.label)
          .append(this.iconSclass, object.iconSclass)
          .toComparison();
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
        AbstractSidebarItem obj = (AbstractSidebarItem) other;
        return new EqualsBuilder()
                .append(label, obj.label)
                .append(iconSclass, obj.iconSclass)
                .append(priority, obj.priority)
                .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(339, 901)
                .append(label)
                .append(iconSclass)
                .append(priority)
                .toHashCode();
    }

}
