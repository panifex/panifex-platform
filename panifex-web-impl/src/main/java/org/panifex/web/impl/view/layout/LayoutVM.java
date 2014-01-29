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
package org.panifex.web.impl.view.layout;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.panifex.module.api.menu.MenuAction;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.web.impl.content.ContentManager;
import org.panifex.web.impl.menu.AppMenuServiceHolder;
import org.panifex.web.impl.menu.MenuNavitem;
import org.panifex.web.impl.view.settings.SettingsContentView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zul.Div;
import org.zkoss.zul.TreeNode;

/**
 * View-Model of an abstract window which is drawn by AbstractRichlet.
 *
 */
public abstract class LayoutVM {

    // commands
    public static final String LOGOUT_COMMAND = "logout";
    public static final String ON_MENU_ACTION_CLICK = "onMenuActionClick";
    public static final String ON_SETTINGS_CLICK = "onSettingsClick";
    
    // attributes
    public static final String MENU_ITEMS_ATTR = "menuItems"; 
    public static final String IS_USER_LOGGED_IN = "isUserLoggedIn";
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Wire("#content")
    private Div content;
    @Wire("#appmenu")
    private Navbar navbar;
    
    @Command(LOGOUT_COMMAND)
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        log.info("User {} is logging out", currentUser.getPrincipal());
        currentUser.logout();
        Executions.sendRedirect("/zk/login");
    }

    /**
     * Returns if the user is logged in.
     * 
     * @return true if the user is logged in, or false if it is not
     */
    public boolean getIsUserLoggedIn() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isAuthenticated();
    }
    
    /**
     * This method is called when the bookmark is being changed.
     * 
     * The method calls ContentManager which changes the content.
     */
    @Listen("onBookmarkChange = #main")
    public void onBookmarkChange() {
        cleanContent();
        
        // create content
        String contentId = Executions.getCurrent().getDesktop().getBookmark();
        
        ContentManager manager = ContentManager.getManager();
        if (manager != null) {
            ContentManager.getManager().render(content, contentId);
            
            // select application menu action
            selectOpenContentMenuAction(contentId);
        } else {
            log.error("Content manager is null");
        }
    }
    
    /**
     * Selects open content menu action based on content id
     * 
     * @param contentId content id
     */
    private void selectOpenContentMenuAction(String contentId) {
        if (!selectOpenContentMenuAction0(contentId, navbar.getChildren())) {
            // unselect menu action
            navbar.clearSelection();
        }
    }
    
    private boolean selectOpenContentMenuAction0(String contentId, List<Component> components) {
        for (Component item : navbar.getChildren()) {
            if (item instanceof MenuNavitem) {
                MenuNavitem menuItem = (MenuNavitem) item;
                if (menuItem.getBookmark().equals(contentId)) {
                    navbar.setSelectedItem(menuItem);
                    return true;
                }
            } else if (item instanceof Nav) {
                Nav nav = (Nav) item;
                if (selectOpenContentMenuAction0(contentId, nav.getChildren())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<TreeNode<MenuItem>> getMenuItems() {
        return AppMenuServiceHolder.getMenuItems();
    }
    
    @Command(ON_MENU_ACTION_CLICK)
    public void onMenuActionClick(@BindingParam(MenuAction.ID) MenuAction action) {
        action.onClick();
    }
    
    /**
     * Removes children from content
     */
    private void cleanContent() {
        // clear content area
        content.getChildren().clear();
    }
    
    /**
     * Shows the {@link org.panifex.web.impl.view.settings.SettingsContentView SettingsContentView}.
     */
    @Command(ON_SETTINGS_CLICK)
    public void onSettingsClick() {
        changeBookmark(SettingsContentView.ID);
    }
    
    private void changeBookmark(String name) {
        Executions.getCurrent().getDesktop().setBookmark(name);
        Events.postEvent(new BookmarkEvent(Events.ON_BOOKMARK_CHANGE, name));
    }
}
