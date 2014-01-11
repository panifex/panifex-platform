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

import java.io.Serializable;
import java.util.List;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.RegistrationListener;
import org.apache.aries.blueprint.annotation.Service;
import org.panifex.module.api.menu.AppMenuService;
import org.panifex.module.api.menu.MenuAction;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.module.api.menu.MenuNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zul.TreeNode;

@Bean(id = AppMenuServiceImpl.ID)
@Service(interfaces = AppMenuService.class, 
    registerationListeners = @RegistrationListener(ref = AppMenuManager.ID))
public final class AppMenuServiceImpl implements AppMenuService, Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 652695490192648820L;

    public static final String ID = "org.panifex.web.impl.menu.AppMenuServiceImpl";
    
    private Logger log = LoggerFactory.getLogger(AppMenuServiceImpl.class);
    
    private MenuTreeModel model = new MenuTreeModel();
    
    @Override
    public void bindMenuAction(MenuAction menuAction) {
        log.debug("Bind MenuAction: {}", menuAction);
        bindItem(menuAction);
    }

    @Override
    public void bindMenuNode(MenuNode menuNode) {
        log.debug("Bind MenuNode: {}", menuNode);
        bindItem(menuNode);
    }

    private void bindItem(MenuItem item) {
        synchronized (model) {
            model.addItem(item);
        }
    }
        
    @Override
    public void unbindMenuAction(MenuAction menuAction) {
        log.debug("Unbind MenuAction: {}", menuAction);
        unbindItem(menuAction);
    }

    @Override
    public void unbindMenuNode(MenuNode menuNode) {
        log.debug("Unbind MenuNode: {}", menuNode);
        unbindItem(menuNode);
    }

    private void unbindItem(MenuItem item) {
        synchronized (model) {
            model.removeItem(item);
        }
    }

    @Override
    public List<TreeNode<MenuItem>> getMenuItems() {
        return model.getRoot().getChildren();
    }
    
}
