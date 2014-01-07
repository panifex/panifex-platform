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
package org.panifex.module.api.menu;

import java.util.List;

import org.zkoss.zul.TreeNode;

/**
 * An {@link AppMenuService} provides methods to dynamic manage the application menu.
 * <p>
 * It is used for binding and unbinding the menu's {@link MenuAction actions} and
 * {@link MenuNode nodes}.
 *
 * @since 1.0
 */
public interface AppMenuService {

    /**
     * Binds the {@link MenuAction} to the application menu.
     * 
     * @param menuAction the {@link MenuAction} to be binded to the application menu
     * 
     * @since 1.0
     */
    void bindMenuItem(MenuAction menuAction);
    
    /**
     * Binds the {@link MenuNode} to the application menu.
     * 
     * @param menuNode the {@link MenuNode} to be binded to the application menu
     * 
     * @since 1.0
     */
    void bindMenuItem(MenuNode menuNode);
    
    /**
     * Unbinds the {@link MenuAction} from the application menu.
     * 
     * @param menuAction the {@link MenuAction} to be unbinded from the application menu
     * 
     * @since 1.0
     */
    void unbindMenuItem(MenuAction menuAction);
    
    /**
     * Unbinds the {@link MenuNode} from the application menu.
     * 
     * @param menuNode the {@link MenuNode} to be unbinded from the application menu
     * 
     * @since 1.0
     */
    void unbindMenuItem(MenuNode menuNode);
    
    /**
     * Returns the currently binded menu's {@link MenuAction actions} and
     * {@link MenuNode nodes}.
     * 
     * @return the currently binded menu's items
     * 
     * @since 1.0
     */
    List<TreeNode<MenuItem>> getMenuItems();
}
