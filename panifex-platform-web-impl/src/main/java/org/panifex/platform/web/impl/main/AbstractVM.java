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
package org.panifex.platform.web.impl.main;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jfree.util.Log;
import org.panifex.platform.module.api.sidebar.SidebarCommand;
import org.panifex.platform.module.api.sidebar.SidebarItem;
import org.panifex.platform.web.impl.content.ContentManager;
import org.panifex.platform.web.impl.sidebar.SidebarManager;
import org.slf4j.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

/**
 * View-Model of an abstract window which is drawn by AbstractRichlet.
 *
 */
public abstract class AbstractVM {

    public final static String ON_SIDEBAR_ITEM_CLICK = "'onSidebarItemClick'";
    
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

    public boolean getIsUserLoggedIn() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.isAuthenticated();
    }

    public List<SidebarItem> getSidebarItems() {
        return SidebarManager.getManager().getSidebarItems();
    }
    
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
            Log.error("Content manager is null");
        }
    }
    
    @Command()
    public void onSidebarItemClick(@BindingParam(SidebarCommand.ID) SidebarCommand command) {
        command.onClick();
    }
    
    /**
     * Removes children from content
     */
    private void cleanContent() {
        content.getChildren().clear();
    }
}
