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
import org.panifex.web.impl.menu.AppMenuManager;
import org.panifex.web.impl.view.settings.SettingsContent;
import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.TreeNode;

/**
 * View-Model of an abstract window which is drawn by AbstractRichlet.
 *
 */
public abstract class LayoutVM {

    public final static String ON_MENU_ACTION_CLICK = "'onMenuActionClick'";
    
    protected abstract Logger getLogger();
    
    @Wire("#content")
    private Div content;
    
    @Command
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        getLogger().info("User {} is logging out", currentUser.getPrincipal());
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
        String bookmark = Executions.getCurrent().getDesktop().getBookmark();
        
        ContentManager manager = ContentManager.getManager();
        if (manager != null) {
            Component newContent = ContentManager.getManager().render(bookmark);
            content.appendChild(newContent);
        } else {
            getLogger().error("Content manager is null");
        }
    }

    public List<TreeNode<MenuItem>> getMenuItems() {
        return AppMenuManager.getMenuItems();
    }
    
    @Command()
    public void onMenuActionClick(@BindingParam(MenuAction.ID) MenuAction action) {
        action.onClick();
    }
    
    /**
     * Removes children from content
     */
    private void cleanContent() {
        content.getChildren().clear();
    }
    
    @Command
    public void onSettingsClick() {
        changeBookmark(SettingsContent.ID);
    }
    
    private void changeBookmark(String name) {
        Executions.getCurrent().getDesktop().setBookmark(name);
        Events.postEvent(new BookmarkEvent(Events.ON_BOOKMARK_CHANGE, name));
    }
}
