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

public interface HtmlComponent {

    /**
     * Adds one or more style names to this component. Multiple styles can be specified as
     * a space-separated list of style names. The style name will be rendered as a HTML class
     * name, which can be used in a CSS definition.
     *
     * @param styleName
     *      the new style name or the new style names to be added to the component
     */
    void addStyleName(String styleName);

    /**
     * Removes one or more style names from component. Multiple styles can be specified as
     * a space-separated list of style names. The parameter must be a valid CSS style name. Only
     * user-defined style names added with addStyleName() or setStyleName() can be removed;
     * built-in style names defined in Vaadin or GWT can not be removed.
     *
     * @param styleName
     *      the style name of the style names to be removed from the component
     */
    void removeStyleName(String styleName);

    /**
     * Adds an unique id for component that is used in the client-side for testing purposes.
     * Keeping identifiers unique is the responsibility of the programmer.
     *
     * @param id an alphanumeric id
     */
    void setId(String id);
}
