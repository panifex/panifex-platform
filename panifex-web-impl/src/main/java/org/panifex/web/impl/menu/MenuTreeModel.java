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
import org.apache.commons.lang3.StringUtils;
import org.panifex.module.api.menu.MenuAction;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.module.api.menu.MenuNode;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

/**
 * A {@link org.zkoss.zul.DefaultTreeModel DefaultTreeModel} implementation
 * which contains {@link org.panifex.module.api.menu.MenuItem MenuItem} items.
 * <p>
 * This model is binded to the application menu.
 */
public final class MenuTreeModel extends DefaultTreeModel<MenuItem> {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 6060148797000989253L;

    /**
     * Contains the all {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * which have not been added to the model yet.
     * <p>
     * Some items could not added because their parent node is not added yet.
     * <p>
     * The {@link org.apache.commons.collections.MultiMap MultiMap} is indexed
     * per {@link org.panifex.module.api.menu.MenuItem#getParentId() MenuItem#getParentId()}
     * values. It is used because there could be more than one {@link org.panifex.module.api.menu.MenuItem MenuItem}
     * with the same parent ID.
     */
    private MultiMap queuedItems = new MultiValueMap();
    
    /**
     * Constructs a new {@link MenuTreeModel} instance.
     */
    public MenuTreeModel() {
        super(new DefaultTreeNode<MenuItem>(
                null, 
                new ArrayList<DefaultTreeNode<MenuItem>>()));
    }
    
    /**
     * Adds the {@link org.panifex.module.api.menu.MenuItem MenuItem} to
     * the model.
     * 
     * @param item the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be added to the model
     */
    public void addItem(MenuItem item) {
        if (!addMenuItemToTreeNode(getRoot(), item)) {
            String parentId = StringUtils.defaultString(item.getParentId());
            queuedItems.put(parentId, item);
        }
        
    }
    
    /**
     * Adds the collection of {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * to the {@link org.zkoss.zul.TreeNode TreeNode}.
     * <p>
     * It the {@link org.panifex.module.api.menu.MenuItem MenuItem} can not be added,
     * because the its parent has not yet been added, it puts the {@link org.panifex.module.api.menu.MenuItem MenuItem}
     * to the {@link MenuTreeModel#queuedItems} collection.
     * 
     * @param treeNode the {@link org.zkoss.zul.TreeNode TreeNode} in which will be the collection of {@link org.panifex.module.api.menu.MenuItem MenuItem}s added
     * @param menuItems the collection of {@link org.panifex.module.api.menu.MenuItem MenuItem}s to be added to the {@link org.zkoss.zul.TreeNode TreeNode}
     */
    private void addMenuItemsListToTreeNode(
            TreeNode<MenuItem> treeNode, 
            @SuppressWarnings("rawtypes") Collection menuItems) {
        
        for (Object menuItem : menuItems) {
            // add the MenuItem to the TreeNode
            if (addMenuItemToTreeNode(treeNode, (MenuItem) menuItem)) {
                // the MenuItem has been successfully added, remove it from the 
                // queuedItems list
                queuedItems.remove(((MenuItem) menuItem).getParentId(), menuItem);
            }
        }
    }
    
    /**
     * Adds the {@link org.panifex.module.api.menu.MenuItem MenuItem} 
     * to the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s.
     * 
     * @param parentTreeNodes the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s in which will be the {@link org.panifex.module.api.menu.MenuItem MenuItem} added
     * @param menuItem the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be added to the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s
     * 
     * @return true if the {@link org.panifex.module.api.menu.MenuItem MenuItem} is added to the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s, of false if it is not
     */
    private boolean addMenuItemToTreeNodeList(List<TreeNode<MenuItem>> parentTreeNodes, MenuItem menuItem) {
        for (TreeNode<MenuItem> parentTreeNode : parentTreeNodes) {
            // add the MenuItem to the TreeNode
            boolean isAdded = addMenuItemToTreeNode(parentTreeNode, menuItem);
            if (isAdded) {
                // the MenuItem has been successfully added, return true
                return true;
            }
            // the MenuItem has not been added, try to add it to the another tree node
        }
        
        // the MenuItem has not been successfully added, return false
        return false;
    }
    
    /**
     * Adds the {@link org.panifex.module.api.menu.MenuItem MenuItem} 
     * to the {@link org.zkoss.zul.TreeNode TreeNode}s.
     * 
     * @param parentTreeNode the {@link org.zkoss.zul.TreeNode TreeNode} in which will be the {@link org.panifex.module.api.menu.MenuItem MenuItem} added
     * @param menuItem the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be added to the {@link org.zkoss.zul.TreeNode TreeNode}
     * 
     * @return true if the {@link org.panifex.module.api.menu.MenuItem MenuItem} is added to the {@link org.zkoss.zul.TreeNode TreeNode}s, of false if it is not
     */
    @SuppressWarnings("unchecked")
    private boolean addMenuItemToTreeNode(TreeNode<MenuItem> parentTreeNode, MenuItem menuItem) {

        // get the tree node's menuItem id
        String treeNodeId = StringUtils.EMPTY;
        
        // the parent node's data could be null, so perform checking before
        // using its data
        if (parentTreeNode.getData() != null) {
            treeNodeId = StringUtils.defaultString(parentTreeNode.getData().getId());
        }
        
        // get the menuItem's ID
        String itemParentId = StringUtils.defaultString(menuItem.getParentId());
        
        // check if the tree node contains the menu node which is the parent of the menu item
        if (treeNodeId.equals(itemParentId)) {
            // the tree node contains the menu node which is the parent of the menu item,
            // so add the menu item to the tree node
            if (menuItem instanceof MenuNode) {
                // create a new tree node which contains the menu item
                DefaultTreeNode<MenuItem> newTreeNode = 
                        new DefaultTreeNode<>(menuItem, new ArrayList<DefaultTreeNode<MenuItem>>());
                    
                // add the created tree node to the tree node
                parentTreeNode.add(newTreeNode);
                    
                // get all queued item with the same parent id
                @SuppressWarnings("rawtypes")
                Collection childItems = (Collection) queuedItems.get(menuItem.getId());
                if (childItems != null) {
                    // append other items with the same parent id
                    addMenuItemsListToTreeNode(newTreeNode, new ArrayList<MenuItem>(childItems));
                }
                
                // the menu item has been successfully added to the tree node, so return true
                return true;
            } else if (menuItem instanceof MenuAction) {
                // create a new tree node which contains the menu item
                DefaultTreeNode<MenuItem> newTreeNode = new DefaultTreeNode<MenuItem>(menuItem);
                
                // add the created tree node to the tree node
                parentTreeNode.add(newTreeNode);
                
                // the menu item has been successfully added to the tree node, so return true
                return true;
            } else {
                throw new UnsupportedOperationException("Currently, only MenuNode and MenuAction can be added to the model.");
            }
        } else {
            // the tree node does not contain the menu node which is the parent of the menu item,
            // but try to add it to the tree node's children
            if (!parentTreeNode.isLeaf()) {
                // the parent tree node has children, so try to add the menu item to them
                return addMenuItemToTreeNodeList(parentTreeNode.getChildren(), menuItem);
            } else {
                // the parent tree node does not have any children
                // return false, because it is impossible to try add the menu item to them
                return false;
            }
        }
    }
    
    /**
     * Removes the {@link org.panifex.module.api.menu.MenuItem MenuItem} from
     * the model.
     * 
     * @param item the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be removed from the model
     */
    public void removeItem(MenuItem item) {
        if (queuedItems.remove(item.getParentId(), item) == null) {
            removeChildItem(getRoot().getChildren(), item);
        }
    }
    
    /**
     * Removes the {@link org.panifex.module.api.menu.MenuItem MenuItem}
     * from the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s.
     * 
     * @param treeNodes the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s in which will be the {@link org.panifex.module.api.menu.MenuItem MenuItem} removed
     * @param itemToRemove the {@link org.panifex.module.api.menu.MenuItem MenuItem} to be removed from the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s
     */
    private void removeChildItem(List<TreeNode<MenuItem>> treeNodes, MenuItem itemToRemove) {
        for (TreeNode<MenuItem> treeNode : treeNodes) {
            // get the tree node's data
            MenuItem treeNodeItem = treeNode.getData();
            
            if (treeNodeItem != null) {
                // check if the tree node's item is the same as the itemToRemove
                if (treeNodeItem.equals(itemToRemove)) {
                    // the tree node's item is the same as the itemToRemove
                    
                    if (!treeNode.isLeaf()) {
                     // remove the tree node children, before removing the tree node which contains the itemToRemove
                        removeTreeNodesFromModel(treeNode.getChildren());
                    }
                    
                    // remove the tree node from the model
                    treeNode.getParent().remove(treeNode);
                    
                    // stop processing because the itemToRemove has been removed
                    return;
                } else {
                    // the tree node's item is not the same as the itemToRemove,
                    // so try to remove it from the tree node's children
                    if (!treeNode.isLeaf()) {
                        removeChildItem(treeNode.getChildren(), itemToRemove);
                    }
                }
            }
        }
    }
    
    /**
     * Removes the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s to be removed
     * from the model.
     * <p>
     * Removed {@link org.zkoss.zul.TreeNode TreeNode}'s {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * will be put to the {@link MenuTreeModel#queuedItems} list, because it is not completely
     * removed from the application menu.
     * 
     * @param treeNodes the collection of the {@link org.zkoss.zul.TreeNode TreeNode}s to be removed from the model
     */
    private void removeTreeNodesFromModel(List<TreeNode<MenuItem>> treeNodes) {
        for (TreeNode<MenuItem> treeNode : treeNodes) {
            // put the TreeNode's MenuItem to the queuedItems list
            queuedItems.put(treeNode.getData().getParentId(), treeNode.getData());
            
            // remove the tree node's children from the model too
            if (!treeNode.isLeaf()) {
                removeTreeNodesFromModel(treeNode.getChildren());
            }
        }
        
        // clear the tree nodes list
        treeNodes.clear();
    }

}
