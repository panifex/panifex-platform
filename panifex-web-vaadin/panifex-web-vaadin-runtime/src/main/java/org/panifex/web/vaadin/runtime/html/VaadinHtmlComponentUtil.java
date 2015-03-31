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
package org.panifex.web.vaadin.runtime.html;

import org.panifex.web.spi.html.HtmlComponent;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

public class VaadinHtmlComponentUtil {

    /**
     * Adds the component to the container.
     *
     * @param htmlComp the component to be added to the container
     * @param container the container that the component to be added into
     */
    public static void addComponentToContainer(
            HtmlComponent htmlComp, ComponentContainer container) {
        Component comp = castHtmlComponent(htmlComp, Component.class);
        container.addComponent(comp);
    }

    /**
     * Removes the component from the container.
     *
     * @param htmlComp the component to be removed from the container
     * @param container the container from the component to be removed
     */
    public static void removeComponentFromContainer(
            HtmlComponent htmlComp, ComponentContainer container) {
        Component comp = castHtmlComponent(htmlComp, Component.class);
        container.removeComponent(comp);
    }

    /**
     * Casts the {@link HtmlComponent} instance as the {@link Component} instance, or throws
     * an IllegalArgumentException if it cannot be cast.
     *
     * @param htmlComp the html component to be cast
     * @return  the casted html component
     */
    @SuppressWarnings("unchecked")
    public static <T> T castHtmlComponent(HtmlComponent htmlComp, Class<T> compClass) {
        if (compClass.isInstance(htmlComp)) {
            return (T) htmlComp;
        } else {
            String msg = new StringBuilder("HtmlComponent must be subclassed from ").
                    append(compClass.getCanonicalName()).
                    append(" class").
                    toString();
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Private constructor protects from instancing - singleton
     */
    private VaadinHtmlComponentUtil() {
        // do nothing
    }
}
