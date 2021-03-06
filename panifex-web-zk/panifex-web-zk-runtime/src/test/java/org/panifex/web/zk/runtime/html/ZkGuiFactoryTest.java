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
package org.panifex.web.zk.runtime.html;

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
import org.zkoss.bind.DefaultBinder;
import org.zkoss.zk.ui.Page;

/**
 * Unit tests for {@link ZkGuiFactory} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ZkGuiFactory.class)
public class ZkGuiFactoryTest extends GuiFactoryTestSupport {

    private GuiFactory guiFactory;

    @Before
    public void setUp() {
        resetAll();

        guiFactory = new ZkGuiFactory();
    }

    @Test
    @Override
    public void testSetValidPageContent() throws Exception {
        // mocks
        ZkHorizontalLayout layout = createMock(ZkHorizontalLayout.class);
        Page request = createMock(Page.class);

        // expect setting page content
        layout.setPage(request);

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
        Page request = createMock(Page.class);

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
        ZkHorizontalLayout layout = createMock(ZkHorizontalLayout.class);
        Object viewModel = createMock(Object.class);

        // expect creating binder
        DefaultBinder binder = createMockAndExpectNew(DefaultBinder.class);
        binder.init(layout, viewModel, null);
        expect(layout.setAttribute(ZkGuiFactory.VM_BIND_ID + 1, viewModel)).andReturn(createMock(Object.class));

        // perform test
        replayAll();
        guiFactory.initViewModelBinding(viewModel, layout);
        verifyAll();
    }

    @Override
    @Test
    public void testBindProperty() throws Exception {
        // variables
        String propId = getRandomChars(20);
        String expr = new StringBuilder(ZkGuiFactory.VM_BIND_ID).
                append(1).
                append('.').
                append(propId).
                toString();

        // mocks
        ZkTextField textField = createMock(ZkTextField.class);
        Object viewModel = createMock(Object.class);

        // expect creating binder
        DefaultBinder binder = createMockAndExpectNew(DefaultBinder.class);
        binder.init(textField, viewModel, null);
        expect(textField.setAttribute(ZkGuiFactory.VM_BIND_ID + 1, viewModel)).andReturn(createMock(Object.class));

        // expect binding property
        binder.addPropertyLoadBindings(textField, "value", expr, null, null, null, null, null);
        binder.addPropertySaveBindings(textField, "value", expr, null, null, null, null, null, null, null);

        // expect getting view model from binder
        expect(binder.getViewModel()).andReturn(viewModel);

        // perform test
        replayAll();
        guiFactory.initViewModelBinding(viewModel, textField);

        // bind property
        guiFactory.bindProperty(viewModel, propId, textField);
        verifyAll();

    }

    @Override
    public void testLoadComponent() throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    @Test
    public void testCreateButton() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating button
        ZkButton expectedButton = createMockAndExpectNew(ZkButton.class, compId);

        // expect adding button to container
        container.addHtmlComponent(expectedButton);

        // perform test
        replayAll();
        Button createdButton = guiFactory.createButton(compId, container);
        verifyAll();

        assertEquals(expectedButton, createdButton);
    }

    @Override
    @Test
    public void testCreateHorizontalLayout() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // expect creating layout
        ZkHorizontalLayout expectedLayout = createMockAndExpectNew(ZkHorizontalLayout.class, compId);

        // perform test
        replayAll();
        HorizontalLayout createdLayout = guiFactory.createHorizontalLayout(compId);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    @Override
    public void testCreateHorizontalLayoutInsideParentContainer() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating layout
        ZkHorizontalLayout expectedLayout = createMockAndExpectNew(ZkHorizontalLayout.class, compId);

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
    public void testCreatePasswordField() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating password field
        ZkPasswordField expectedPasswordField = createMockAndExpectNew(ZkPasswordField.class, compId);

        // expect adding password field to container
        container.addHtmlComponent(expectedPasswordField);

        // perform test
        replayAll();
        PasswordField createdPasswordField = guiFactory.createPasswordField(compId, container);
        verifyAll();

        assertEquals(expectedPasswordField, createdPasswordField);
    }

    @Override
    @Test
    public void testCreateVerticalLayout() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // expect creating layout
        ZkVerticalLayout expectedLayout = createMockAndExpectNew(ZkVerticalLayout.class, compId);

        // perform test
        replayAll();
        VerticalLayout createdLayout = guiFactory.createVerticalLayout(compId);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    @Override
    public void testCreateVerticalLayoutInsideParentContainer()
            throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating layout
        ZkVerticalLayout expectedLayout = createMockAndExpectNew(ZkVerticalLayout.class, compId);

        // expect adding layout to parent container
        container.addHtmlComponent(expectedLayout);

        // perform test
        replayAll();
        VerticalLayout createdLayout = guiFactory.createVerticalLayout(compId,container);
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Override
    @Test
    public void testCreateTextField() throws Exception {
        // variables
        String compId = getRandomChars(20);

        // mocks
        Container container = createMock(Container.class);

        // expect creating text field
        ZkTextField expectedTextField = createMockAndExpectNew(ZkTextField.class, compId);

        // expect adding text field to container
        container.addHtmlComponent(expectedTextField);

        // perform test
        replayAll();
        TextField createdTextField = guiFactory.createTextField(compId, container);
        verifyAll();

        assertEquals(expectedTextField, createdTextField);
    }
}
