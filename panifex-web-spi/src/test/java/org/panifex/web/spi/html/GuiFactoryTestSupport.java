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

import org.panifex.test.support.TestSupport;

/**
 * The template class for unit tests of {@link GuiFactory} implementations.
 */
public abstract class GuiFactoryTestSupport extends TestSupport {

    /**
     * Tests the {@link GuiFactory#setPageContent(Object, HtmlComponent)} method.
     * <p>
     * Creates a new valid html content and sets it as a page content.
     * <p>
     * The html content must be set as the page content.
     */
    public abstract void testSetValidPageContent() throws Exception;

    /**
     * Tests the {@link GuiFactory#setPageContent(Object, HtmlComponent)} method.
     * <p>
     * Creates a new invalid html content that cannot be set as a page content.
     * <p>
     * The IllegalArgumentException must be thrown.
     */
    public abstract void testSetInvalidPageContent() throws Exception;

    /**
     * Tests the {@link GuiFactory#initViewModelBinding(Object, HtmlComponent)} method.
     * <p>
     * Creates the view-model implementation and binds it to the html component.
     * <p>
     * The view-model must be binded to the html component.
     */
    public abstract void testInitViewModelBinding() throws Exception;

    /**
     * Tests the {@link GuiFactory#bindProperty(Object, String, ValueComponent)} method.
     */
    public abstract void testBindProperty() throws Exception;

    /**
     * Tests the {@link GuiFactory#loadComponent(Object, HtmlComponent)} method.
     */
    public abstract void testLoadComponent() throws Exception;

    /**
     * Tests the {@link GuiFactory#createButton(String, Container)} method.
     * <p>
     * The button must be created inside the parent container component..
     */
    public abstract void testCreateButton() throws Exception;

    /**
     * Tests the {@link GuiFactory#createHorizontalLayout(String)} method.
     * <p>
     * The horizontal layout component must be created.
     */
    public abstract void testCreateHorizontalLayout() throws Exception;

    /**
     * Tests the {@link GuiFactory#createHorizontalLayout(String, Container)} method.
     * <p>
     * The horizontal layout must be created inside the parent container component.
     */
    public abstract void testCreateHorizontalLayoutInsideParentContainer() throws Exception;

    /**
     * Tests the {@link GuiFactory#createPasswordField(String, Container)} method.
     * <p>
     * The password field must be created inside the parent container component.
     */
    public abstract void testCreatePasswordField() throws Exception;

    /**
     * Tests the {@link GuiFactory#createVerticalLayout(String)} method.
     * <p>
     * The vertical layout must be created.
     */
    public abstract void testCreateVerticalLayout() throws Exception;

    /**
     * Tests the {@link GuiFactory#createVerticalLayout(String, Container)} method.
     * <p>
     * The vertical layout must be created inside the parent container method.
     */
    public abstract void testCreateVerticalLayoutInsideParentContainer() throws Exception;

    /**
     * Tests the {@link GuiFactory#createTextField(String, Container)} method.
     * <p>
     * The text field must be created inside the parent container method..
     */
    public abstract void testCreateTextField() throws Exception;
}
