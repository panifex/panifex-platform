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
 * A content of the web application.
 * <p>
 * The content is shown in the center of the application's GUI.
 *
 * @since 1.0
 */
public interface Content {

    /**
     * Returns the title of content.
     *
     * @return the title of content
     * @since 1.0
     */
    String getTitle();

    /**
     * Returns the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>.
     *
     * <p> The content does not have to be assigned to any bookmark.
     *
     * @return the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     *
     * @since 1.0
     */
    String getBookmark();

    /**
     * Creates the body of content.
     * <p>
     * Every class which implements this interface must implement this method to
     * define an unique component set.
     *
     * @param parent
     *      the {@link org.zkoss.zk.ui.Component Component} in which the method should append specific content
     */
    void createBody(final Object parent);
}
