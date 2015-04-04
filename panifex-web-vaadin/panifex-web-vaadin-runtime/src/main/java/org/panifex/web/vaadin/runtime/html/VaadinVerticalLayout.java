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

import com.vaadin.ui.VerticalLayout;

/**
 * A vertical layout component.
 */
public class VaadinVerticalLayout
        extends VerticalLayout
        implements org.panifex.web.spi.html.VerticalLayout {

    /**
     * Creates a new vertical layout component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     */
    public VaadinVerticalLayout(String id) {
        setId(id);
    }

    @Override
    public void addHtmlComponent(HtmlComponent component) {
        VaadinHtmlComponentUtil.addComponentToContainer(component, this);
    }

    @Override
    public void removeHtmlComponent(HtmlComponent component) {
        VaadinHtmlComponentUtil.removeComponentFromContainer(component, this);
    }
}
