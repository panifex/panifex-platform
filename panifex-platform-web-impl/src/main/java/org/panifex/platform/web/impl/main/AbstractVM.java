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

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.panifex.platform.module.api.sidebar.AbstractSidebarItem;
import org.panifex.platform.web.impl.sidebar.SidebarManager;
import org.slf4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public abstract class AbstractVM {

    protected abstract Logger getLogger();

    private SidebarManager sidebarManager;

    public AbstractVM() {
        try {
            InitialContext ctx = new InitialContext();
            sidebarManager = (SidebarManager) ctx.lookup("blueprint:comp/" + SidebarManager.ID);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        };
    }

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

    public List<AbstractSidebarItem> getSidebarItems() {
        return sidebarManager.getSidebarItems();
    }
}
