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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.panifex.module.api.menu.MenuAction;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.module.api.menu.MenuNode;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

public final class MenuTreeModel extends DefaultTreeModel<MenuItem> {

    /**
     * Serial UID
     */
    private static final long serialVersionUID = 6060148797000989253L;

    private MultiMap items = new MultiValueMap();
    
    public MenuTreeModel() {
        super(new DefaultTreeNode<MenuItem>(null, new ArrayList<DefaultTreeNode<MenuItem>>()));
    }
    
    public void addItem(MenuItem item) {
        TreeNode<MenuItem> root = getRoot();
        
        String parentId = getParentId(item);
        
        items.put(parentId, item);
        
        if (parentId.equals("")) {
            root.add(new DefaultTreeNode<MenuItem>(item));
        } else {
            addChildItem(root.getChildren(), item);
        }
    }
    
    private void addChildItem(List<TreeNode<MenuItem>> items, MenuItem newItem) {
        for (TreeNode<MenuItem> item : items) {
            addChildItem(item, newItem);
        }
    }
    
    private void addChildItems(
            TreeNode<MenuItem> item, 
            @SuppressWarnings("rawtypes") Collection newItems) {
        
        for (Object newItem : newItems) {
            addChildItem(item, (MenuItem) newItem);
        }
    }
    
    private void addChildItem(TreeNode<MenuItem> item, MenuItem newItem) {
        if (!item.isLeaf()) {
            if (item.getData().getId().equals(newItem.getParentId())) {
                if (newItem instanceof MenuNode) {
                    item.add(new DefaultTreeNode<MenuItem>(newItem, new ArrayList<DefaultTreeNode<MenuItem>>()));
                    
                    // append other items with the same parent id
                    @SuppressWarnings("rawtypes")
                    Collection otherItems = (Collection) items.get(newItem.getId());
                    addChildItems(item, otherItems);
                } else if (newItem instanceof MenuAction) {
                    item.add(new DefaultTreeNode<MenuItem>(newItem));
                } else {
                    throw new UnsupportedOperationException();
                }
            } else {
                addChildItem(item.getChildren(), newItem);
            }
        }
    }
    
    public void removeItem(MenuItem item) {
        items.remove(item.getId(), item);
        removeChildItem(getRoot().getChildren(), item);
    }
    
    private void removeChildItem(List<TreeNode<MenuItem>> items, MenuItem removedItem) {
        for (TreeNode<MenuItem> item : items) {
            if (item.getData().equals(removedItem)) {
                item.getParent().remove(item);
            } else {
                if (!item.isLeaf()) {
                    removeChildItem(item.getChildren(), removedItem);
                }
            }
        }
    }
    
    private String getParentId(MenuItem item) {
        return (item.getParentId() != null) ? item.getParentId() : "";
    }
}
