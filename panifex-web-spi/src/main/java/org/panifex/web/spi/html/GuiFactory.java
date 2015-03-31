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

public interface GuiFactory<Request> {

    /**
     * Sets the content of page container.
     *
     * @param request
     * @param content the content to be set
     */
    void setPageContent(Request request, HtmlComponent htmlComp);

    void initViewModelBinding(Object viewModel, HtmlComponent htmlComp);

    void bindProperty(Object viewModel, String propertyId, ValueComponent<?> valueComp);

    Button createButton();

    HorizontalLayout createHorizontalLayout();

    VerticalLayout createVerticalLayout();

    TextField createTextField();
}
