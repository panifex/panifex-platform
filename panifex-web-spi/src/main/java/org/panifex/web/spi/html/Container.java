/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.web.spi.html;

/**
 * A html container componet that can contain child html components.
 */
public interface Container extends HtmlComponent {

    /**
     * Adds the component to this component container.
     *
     * @param htmlComp the component to be added to this container
     */
    void addHtmlComponent(HtmlComponent htmlComp);

    /**
     * Removes the component from this component container.
     *
     * @param htmlComp the component to be removed from this container
     */
    void removeHtmlComponent(HtmlComponent htmlComp);
}
