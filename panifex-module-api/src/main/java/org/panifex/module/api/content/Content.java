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
package org.panifex.module.api.content;

import org.zkoss.zk.ui.Component;

/**
 * Content of web application.
 * 
 * @since 1.0
 */
public interface Content {

    /**
     * Returns the content title
     * 
     * @return the content title
     * @since 1.0
     */
    String getTitle();
    
    /**
     * Returns an assigned bookmark. 
     * 
     * <p> The content doesn't have to be assigned to any bookmark.
     * 
     * @return the assigned bookmark
     * @since 1.0
     */
    String getBookmark();
    
    /**
     * Creates a body of content.
     * 
     * @return the ZK Component which represents body
     */
    Component createBody();
}
