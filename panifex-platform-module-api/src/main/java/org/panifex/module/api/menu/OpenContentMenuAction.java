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

import org.panifex.module.api.environment.EnvironmentManager;

public final class OpenContentMenuAction extends AbstractMenuItem implements MenuAction {

    private final String contentId;
    
    /**
     * Creates a new Open Content Menu Action
     * 
     * @param id action id
     * @param contentId content id
     */
    public OpenContentMenuAction(String id, String contentId) {
        super(id);
        this.contentId = contentId;
    }
    
    
    /**
     * Creates a new Open Content Menu Action
     * 
     * @param id action id
     * @param contentId content id
     * @param priority position in a node
     */
    public OpenContentMenuAction(String id, String contentId, int priority) {
        super(id, priority);
        this.contentId = contentId;
    }
    
    /**
     * Creates a new Open Content Menu Action
     * 
     * @param id action id
     * @param parentId parent node id
     * @param contentId content id
     */
    public OpenContentMenuAction(String id, String parentId, String contentId) {
        super(id, parentId);
        this.contentId = contentId;
    }
    
    /**
     * Creates a new Open Content Menu Action
     * 
     * @param id action id
     * @param parentId parent node id
     * @param contentId content id
     * @param priority position in a node
     */
    public OpenContentMenuAction(String id, String parentId, String contentId, int priority) {
        super(id, parentId, priority);
        this.contentId = contentId;
    }
    
    @Override
    public void onClick() {
        setBookmark(contentId);
    }

    private void setBookmark(String bookmark) {
        EnvironmentManager.getService().setBookmark(bookmark);
    }
}
