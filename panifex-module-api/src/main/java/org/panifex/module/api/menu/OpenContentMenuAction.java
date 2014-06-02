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

import org.panifex.module.api.environment.Environment;

/**
 * A {@link MenuAction} which shows the defined {@link org.panifex.module.api.content.Content Content}
 * based on the <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmarks</a>.
 * <p>
 * When the user clicks on it, it sets the bookmark and then the layout manager shows the
 * defined {@link org.panifex.module.api.content.Content Content}.
 * <p>
 * It supports that the user can surf through the web application as in traditional
 * multi-page web applications by BACK and FORWARD buttons.
 *
 * @see <a href="http://books.zkoss.org/wiki/ZK_Developer%27s_Reference/UI_Patterns/Browser_History_Management">Browser's_History_Management</a>
 *
 * @since 1.0
 */
public final class OpenContentMenuAction extends AbstractMenuItem implements MenuAction {

    /**
     * The {@link org.panifex.module.api.content.Content Content}'s
     * bookmark,
     */
    private final String bookmark;

    /**
     * Constructs the new {@link OpenContentMenuAction} instance with the
     * specified ID and the specified {@link org.panifex.module.api.content.Content Content}'s
     * bookmark.
     *
     * @param id the {@link MenuItem}'s ID
     * @param bookmark the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     *
     * @since 1.0
     */
    public OpenContentMenuAction(String id, String bookmark) {
        super(id);
        this.bookmark = bookmark;
    }


    /**
     * Constructs the {@link OpenContentMenuAction} with the specified item's ID,
     * the {@link org.panifex.module.api.content.Content Content}'s bookmark
     * and the priority which defines the position in the same hierarchy level.
     *
     * @param id the {@link MenuItem}'s ID
     * @param bookmark the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     * @param priority the priority which defines the position in the same hierarchy level
     *
     * @since 1.0
     */
    public OpenContentMenuAction(String id, String bookmark, int priority) {
        super(id, priority);
        this.bookmark = bookmark;
    }

    /**
     * Constructs the {@link OpenContentMenuAction} with the specified item's ID,
     * the specified parent {@link MenuNode}'s ID and the
     * {@link org.panifex.module.api.content.Content Content}'s bookmark.
     *
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode}'s ID
     * @param bookmark the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     *
     * @since 1.0
     */
    public OpenContentMenuAction(String id, String parentId, String bookmark) {
        super(id, parentId);
        this.bookmark = bookmark;
    }

    /**
     * Constructs the {@link OpenContentMenuAction} with the specified item's ID,
     * the parent {@link MenuNode}'s ID, the {@link org.panifex.module.api.content.Content Content}'s
     * bookmark and the priority which defines the position in the same hierarchy level.
     *
     * @param id the {@link MenuItem}'s ID
     * @param parentId the parent {@link MenuNode}'s ID
     * @param bookmark the assigned <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     * @param priority the priority which defines the position in the same hierarchy level
     *
     * @since 1.0
     */
    public OpenContentMenuAction(String id, String parentId, String bookmark, int priority) {
        super(id, parentId, priority);
        this.bookmark = bookmark;
    }

    /**
     * Sets the <a href="http://en.wikipedia.org/wiki/Bookmark_(World_Wide_Web)">bookmark</a>
     * when the user clicks on it.
     *
     * @since 1.0
     */
    @Override
    public void onClick() {
        Environment.setBookmark(bookmark);
    }

    /**
     * Returns the {@link org.panifex.module.api.content.Content Content}'s
     * bookmark.
     *
     * @return the {@link org.panifex.module.api.content.Content Content}'s bookmark
     *
     * @since 1.0
     */
    @Override
    public String getBookmark() {
        return bookmark;
    }
}
