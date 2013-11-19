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
package org.panifex.platform.module.api.sidebar;

/**
 * Default implementation of a sidebar command.
 * 
 * @since 1.0
 */
public class DefaultSidebarCommand extends AbstractSidebarItem implements SidebarCommand {

    private enum ActionType {
        CONTENT,
        REDIRECT
    }
    
    private ActionType activeActionType;
    private String contentId;
    
    /**
     * Content ID identifies content which would be shown if user
     * clicks on a sidebar command. 
     * 
     * <p> If content id is set, other actions would be disabled if they 
     * had been set before.
     * 
     * @param contentId identifies content
     * @since 1.0
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
        activeActionType = ActionType.CONTENT;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public DefaultSidebarCommand copy() {
        DefaultSidebarCommand cloned = new DefaultSidebarCommand();
        super.copyValues(this, cloned);
        return cloned;
    }

    public void onClick() {
        switch (activeActionType) {
            case CONTENT:
                setBookmark(contentId);
                break;
            case REDIRECT:
                break;
        }
    }
    
    private void setBookmark(String bookmark) {
    }
}
