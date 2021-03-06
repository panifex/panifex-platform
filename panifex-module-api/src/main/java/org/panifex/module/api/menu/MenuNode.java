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

/**
 * A {@link MenuNode} is a container of other {@link MenuItem items}.
 * <p>
 * It is a building block of the application's menu.
 *
 * @since 1.0
 */
public interface MenuNode extends MenuItem {

    /**
     * Returns the badge text of the {@link org.zkoss.zkmax.zul.Nav}.
     * 
     * @return the badge text of the {@link org.zkoss.zkmax.zul.Nav}.
     * 
     * @since 1.0
     */
    String getBadgeText();
}
