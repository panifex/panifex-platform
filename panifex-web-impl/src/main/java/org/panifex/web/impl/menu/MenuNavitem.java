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

import org.zkoss.zkmax.zul.Navitem;

/**
 * A specialized {@link org.zkoss.zkmax.zul.Navitem Navitem} which contains
 * an additional bookmark property.
 * <p>
 * It is created to support {@link org.panifex.module.api.menu.OpenContentMenuAction OpenContentMenuAction}
 * functionality.
 * 
 * @since 1.0
 */
public final class MenuNavitem extends Navitem {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 8048726554145580742L;
    
    /**
     * The constant which can be used for binding the bookmark property
     * to the GUI components.
     */
    public static final String BOOKMARK_PROPERTY = "bookmark";
    
    /**
     * The {@link org.panifex.module.api.content.Content Content}'s 
     * bookmark,
     */
    private String bookmark;
    
    /**
     * Returns the {@link org.panifex.module.api.content.Content Content}'s 
     * bookmark.
     * 
     * @return the {@link org.panifex.module.api.content.Content Content}'s bookmark
     * 
     * @since 1.0
     */
    public String getBookmark() {
        return bookmark;
    }
    
    /**
     * Sets the {@link org.panifex.module.api.content.Content Content}'s 
     * bookmark.
     * 
     * @param bookmark the {@link org.panifex.module.api.content.Content Content}'s bookmark
     * 
     * @since 1.0
     */
    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }
}
