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

/**
 * 
 * @since 1.0
 */
public abstract class AbstractContent implements Content {

    private String title;
    private String bookmark;
    
    /**
     * Construct new content template.
     * 
     * @param title a content title
     */
    protected AbstractContent(String title) {
        this(title, null);
    }
    
    /**
     * Construct new content template.
     * 
     * @param title a content title
     * @param bookmark a bookmark identificator
     */
    protected AbstractContent(String title, String bookmark) {
        this.title = title;
        this.bookmark = bookmark;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title of content.
     * 
     * @param title the title of content
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

}
