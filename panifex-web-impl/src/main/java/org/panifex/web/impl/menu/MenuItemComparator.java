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

import java.util.Comparator;

import org.panifex.module.api.menu.MenuItem;

/**
 * A comparison function which compares two {@link org.panifex.module.api.menu.MenuItem MenuItem}s.
 * <p>
 * The function firstly compares items' priorities and if they are the same, then it
 * compares the items' labels.
 */
final class MenuItemComparator implements Comparator<MenuItem> {

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, 
     * or a positive integer as the first {@link org.panifex.module.api.menu.MenuItem MenuItem}
     * is less than, equal to, or greater than the second.
     * 
     * @param item1 the first {@link org.panifex.module.api.menu.MenuItem MenuItem} to be compared
     * @param item2 the second {@link org.panifex.module.api.menu.MenuItem MenuItem} to be compared
     */
    @Override
    public int compare(MenuItem item1, MenuItem item2) {
        // compare items' priorities
        int priorityCompare = comparePriority(item1, item2);
        
        if (priorityCompare != 0) {
            // the items do not have the same priority, so return the result
            return priorityCompare;
        }
        
        // the items have the same priority, so compare items' labels
        return compareLabel(item1, item2);
    }

    private int comparePriority(MenuItem item1, MenuItem item2) {
        // extract priorities
        int priority1 = item1.getPriority();
        int priority2 = item2.getPriority();
        
        // compare the items priority
        if (priority1 > priority2)  {
            return 1;
        }
        if (priority1 < priority2) {
            return -1;
        }
        
        return 0;
    }
    
    private int compareLabel(MenuItem item1, MenuItem item2) {
        // extract labels
        String label1 = item1.getLabel();
        String label2 = item2.getLabel();
        
        // compare labels
        return label1.compareTo(label2);
    }
}
