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
import org.zkoss.zul.TreeNode;

/**
 * A comparison function which compares two {@link org.zkoss.zul.TreeNode TreeNode}s.
 * <p>
 * The function compares the {@link org.zkoss.zul.TreeNode TreeNode} which contains
 * {@link org.panifex.module.api.menu.MenuItem MenuItem} objects.
 * <p>
 * The items comparison is based on the {@link MenuItemComparator} implementation.
 */
final class MenuTreeNodeComparator implements Comparator<TreeNode<MenuItem>> {

    /**
     * The comparator which compares two {@link org.panifex.module.api.menu.MenuItem MenuItem}s.
     */
    private MenuItemComparator itemComparator = new MenuItemComparator();
    
    /**
     * Compares its two arguments for order. Returns a negative integer, zero, 
     * or a positive integer as the first {@link org.panifex.module.api.menu.MenuItem MenuItem}
     * is less than, equal to, or greater than the second.
     * 
     * @param treeNode1 the first {@link org.zkoss.zul.TreeNode TreeNode} which 
     * contains the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be compared
     * 
     * @param treeNode2 the second {@link org.zkoss.zul.TreeNode TreeNode} which 
     * contains the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be compared
     */
    @Override
    public int compare(TreeNode<MenuItem> treeNode1, TreeNode<MenuItem> treeNode2) {
        // extract data
        MenuItem item1 = treeNode1.getData();
        MenuItem item2 = treeNode2.getData();
        
        // compare the menu items
        return itemComparator.compare(item1, item2);
    }

}
