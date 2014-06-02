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
package org.panifex.module.api.environment;


/**
 * Provides method to manage environment settings, for example adding bookmark.
 *
 * @since 1.0
 */
public final class Environment {

    /**
     * Private constructor protectes the {@link Environment}
     * from instancing - singleton.
     */
    private Environment() {
    }

    /**
     * Returns the current bookmark (never null).
     *
     * @return the current bookmark
     *
     * @since 1.0
     */
    public static String getBookmark() {
        //return getDesktop().getBookmark();
        return null;
    }

    /**
     * Adds a bookmark to the current {@link org.zkoss.zk.ui.Desktop Desktop}.
     *
     * @param bookmark the bookmark's name
     *
     * @since 1.0
     */
    public static void setBookmark(String bookmark) {
        setBookmark(bookmark, false);
    }

    /**
     * Sets the bookmark to the current {@link org.zkoss.zk.ui.Desktop Desktop}
     * with more control.
     *
     * @param bookmark the bookmark's name
     * @param replace replace the current bookmark, or add new book
     *
     * @since 1.0
     */
    public static void setBookmark(String bookmark, boolean replace) {
        //getDesktop().setBookmark(bookmark, replace);
        //Events.postEvent(new BookmarkEvent(Events.ON_BOOKMARK_CHANGE, bookmark));
    }

    /**
     * Returns the current {@link org.zkoss.zk.ui.Desktop Desktop}.
     *
     * @return the current {@link org.zkoss.zk.ui.Desktop Desktop}
     */
    /*
    private static Desktop getDesktop() {
        return Executions.getCurrent().getDesktop();
    }*/
}
