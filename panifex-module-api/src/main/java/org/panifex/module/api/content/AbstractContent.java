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
 * The template implementation of the {@link Content} which can be
 * assigned to a <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>.
 * <p>
 *
 *
 * @since 1.0
 */
public abstract class AbstractContent implements Content {

    /**
     * The content's title which is used shown at the top of the content.
     */
    private String title;

    /**
     * The assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>.
     */
    private String bookmark;

    /**
     * Construct the new content template with the assigned title.
     *
     * @param title the content title
     *
     * @since 1.0
     */
    protected AbstractContent(String title) {
        this(title, null);
    }

    /**
     * Construct new content template.
     *
     * @param title the content title
     * @param bookmark the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     *
     * @since 1.0
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

    /**
     * Sets the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>.
     *
     * @param bookmark the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     */
    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

}
