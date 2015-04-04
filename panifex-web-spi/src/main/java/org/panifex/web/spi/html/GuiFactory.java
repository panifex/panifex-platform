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
 * A factory service for creating GUI components.
 */
public interface GuiFactory {

    /**
     * Sets the content of page container.
     *
     * @param request
     * @param content the content to be set
     */
    void setPageContent(Object request, HtmlComponent htmlComp);

    void initViewModelBinding(Object viewModel, HtmlComponent htmlComp);

    void bindCommand(Event onEvent, Object viewModel, String commandExpr, ClickableComponent clickableComp);

    void bindProperty(Object viewModel, String propertyId, ValueComponent<?> valueComp);

    void loadComponent(Object viewModel, HtmlComponent htmlComp);

    /**
     * Creates a button component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @param parent the container component in that the created component will be inserted
     * @return the created button component
     */
    Button createButton(String id, Container parent);

    /**
     * Creates  a horizontal layout component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @return the created horizontal layout component
     */
    HorizontalLayout createHorizontalLayout(String id);

    /**
     * Creates a horizontal layout component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @param parent the container component in that the created component will be inserted
     * @return the created horizontal layout component
     */
    HorizontalLayout createHorizontalLayout(String id, Container parent);

    /**
     * Creates a password field component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @param parent the container component in that the created component will be inserted
     * @return the created password field component
     */
    PasswordField createPasswordField(String id, Container parent);

    /**
     * Creates a vertical layout component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @return the created vertical layout component
     */
    VerticalLayout createVerticalLayout(String id);

    /**
     * Creates a vertical layout component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @param parent the container component in that the created component will be inserted
     * @return the created vertical layout component
     */
    VerticalLayout createVerticalLayout(String id, Container parent);

    /**
     * Creates a text field component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     * @param parent the container component in that the created component will be inserted
     * @return the created text field component
     */
    TextField createTextField(String id, Container parent);
}
