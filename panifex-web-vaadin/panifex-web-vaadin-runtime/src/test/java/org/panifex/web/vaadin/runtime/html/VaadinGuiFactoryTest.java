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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.web.spi.html.Button;
import org.panifex.web.spi.html.Container;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.GuiFactoryTestSupport;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.spi.html.PasswordField;
import org.panifex.web.spi.html.TextField;
import org.panifex.web.spi.html.VerticalLayout;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Unit tests for {@link VaadinGuiFactory} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    UI.class,
    VaadinGuiFactory.class})
public class VaadinGuiFactoryTest extends GuiFactoryTestSupport {

    private GuiFactory guiFactory;

    @Before
    public void setUp() {
        mockStatic(UI.class);

        resetAll();

        guiFactory = new VaadinGuiFactory();
    }

    @Test
    @Override
    public void testSetValidPageContent() throws Exception {
        // mocks
        VaadinHorizontalLayout layout = createMock(VaadinHorizontalLayout.class);
        UI ui = createMock(UI.class);
        VaadinRequest request = createMock(VaadinRequest.class);

        // expect setting page content
        expect(UI.getCurrent()).andReturn(ui);
        ui.setContent(layout);

        // perform test
        replayAll();
        guiFactory.setPageContent(request, layout);
        verifyAll();
    }

    @Test(expected = IllegalArgumentException.class)
    @Override
    public void testSetInvalidPageContent() throws Exception {
        // mocks
        HtmlComponent component = createMock(HtmlComponent.class);
        VaadinRequest request = createMock(VaadinRequest.class);

        // perform test
        replayAll();
        try {
            guiFactory.setPageContent(request, component);
            fail("IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException is expected
            verifyAll();
            throw e;
        }
    }

    @Test
    @Override
    public void testInitViewModelBinding() throws Exception {
        // mocks
        HtmlComponent htmlComp = createMock(HtmlComponent.class);
        Object viewModel = createMock(Object.class);

        // expect creating BeanItem
        createMockAndExpectNew(BeanItem.class, viewModel);

        // perform test
        replayAll();

        // init view model binding
        guiFactory.initViewModelBinding(viewModel, htmlComp);

        verifyAll();
    }

    @Test
    @Override
    public void testBindProperty() throws Exception {
        // variables
        String propId = getRandomChars(20);

        // mocks
        VaadinTextField textField = createMock(VaadinTextField.class);
        Object viewModel = createMock(Object.class);
        Property<?> propertyDataSource = createMock(Property.class);

        // expect creating BeanItem
        @SuppressWarnings("unchecked")
        BeanItem<Object> beanItem = createMockAndExpectNew(BeanItem.class, viewModel);

        // expect getting bean property data source
        expect(beanItem.getItemProperty(propId)).andReturn(propertyDataSource);

        // expect getting bean from BeanItem
        expect(beanItem.getBean()).andReturn(viewModel);

        // expect assigning property data source to text field
        textField.setPropertyDataSource(propertyDataSource);

        // perform test
        replayAll();

        // init view model binding
        guiFactory.initViewModelBinding(viewModel, textField);

        // bind property
        guiFactory.bindProperty(viewModel, propId, textField);

        verifyAll();
    }

    @Test
    @Override
    public void testLoadComponent() throws Exception {
        // mocks
        HtmlComponent htmlComp = createMock(HtmlComponent.class);
        Object viewModel = createMock(Object.class);

        // expect creating BeanItem
        @SuppressWarnings("unchecked")
        BeanItem<Object> beanItem = createMockAndExpectNew(BeanItem.class, viewModel);

        // expect getting bean from BeanItem
        expect(beanItem.getBean()).andReturn(viewModel);

        // perform test
        replayAll();

        // init view model binding
        guiFactory.initViewModelBinding(viewModel, htmlComp);

        // load component
        guiFactory.loadComponent(viewModel, htmlComp);

        verifyAll();
    }

    @Test
    @Override
    public void testCreateButton() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating button
        VaadinButton expectedButton = createMockAndExpectNew(VaadinButton.class, compId);

        // expect adding button to container
        container.addHtmlComponent(expectedButton);

        // perform test
        replayAll();
        Button createdButton = guiFactory.createButton(compId, container);
        verifyAll();

        assertEquals(expectedButton, createdButton);
    }

    @Test
    @Override
    public void testCreatePasswordField() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating password field
        VaadinPasswordField expectedPasswordField =
                createMockAndExpectNew(VaadinPasswordField.class, compId);

        // expect adding password field to container
        container.addHtmlComponent(expectedPasswordField);

        // perform test
        replayAll();
        PasswordField createdPasswordField = guiFactory.createPasswordField(compId, container);
        verifyAll();

        assertEquals(expectedPasswordField, createdPasswordField);
    }

    @Test
    @Override
    public void testCreateHorizontalLayout() throws Exception {
        // varibles
        String compId = getRandomChars(20);

        // expect creating layout
        VaadinHorizontalLayout expectedLayout =
                createMockAndExpectNew(VaadinHorizontalLayout.class, compId);

        // perform test
        replayAll();
        HorizontalLayout createdLayout = guiFactory.createHorizontalLayout(compId);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    @Override
    public void testCreateHorizontalLayoutInsideParentContainer() throws Exception {
        // varibles
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating layout
        VaadinHorizontalLayout expectedLayout =
                createMockAndExpectNew(VaadinHorizontalLayout.class, compId);

        // expect adding layout to parent container
        container.addHtmlComponent(expectedLayout);

        // perform test
        replayAll();
        HorizontalLayout createdLayout = guiFactory.createHorizontalLayout(compId, container);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    @Override
    public void testCreateVerticalLayout() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // expect creating layout
        VaadinVerticalLayout expectedLayout =
                createMockAndExpectNew(VaadinVerticalLayout.class, compId);

        // perform test
        replayAll();
        VerticalLayout createdLayout = guiFactory.createVerticalLayout(compId);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    @Override
    public void testCreateVerticalLayoutInsideParentContainer() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating layout
        VaadinVerticalLayout expectedLayout =
                createMockAndExpectNew(VaadinVerticalLayout.class, compId);

        // expect adding layout to parent container
        container.addHtmlComponent(expectedLayout);

        // perform test
        replayAll();
        VerticalLayout createdLayout = guiFactory.createVerticalLayout(compId, container);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    @Override
    public void testCreateTextField() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating text field
        VaadinTextField expectedTextField =
                createMockAndExpectNew(VaadinTextField.class, compId);

        // expect adding text field to parent container
        container.addHtmlComponent(expectedTextField);

        // perform test
        replayAll();
        TextField createdTextField = guiFactory.createTextField(compId, container);
        verifyAll();

        assertEquals(expectedTextField, createdTextField);
    }
}
