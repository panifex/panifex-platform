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
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.spi.html.TextField;
import org.panifex.web.spi.html.VerticalLayout;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
public class VaadinGuiFactoryTest extends TestSupport {

    private final GuiFactory<VaadinRequest> guiFactory = new VaadinGuiFactory();

    @Before
    public void setUp() {
        mockStatic(UI.class);

        resetAll();
    }

    @Test
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
    public void testInitViewModelBinding() throws Exception {
        // mocks
        HtmlComponent component = createMock(HtmlComponent.class);
        Object viewModel = createMock(Object.class);

        // expect creating BeanItem
        createMockAndExpectNew(BeanItem.class, viewModel);

        // perform test
        replayAll();
        guiFactory.initViewModelBinding(viewModel, component);
        verifyAll();
    }

    @Test
    public void testCreateHorizontalLayout() throws Exception {
        VaadinHorizontalLayout expectedLayout = createMockAndExpectNew(VaadinHorizontalLayout.class);

        // perform test
        replayAll();
        HorizontalLayout createdLayout = guiFactory.createHorizontalLayout();
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    public void testCreateVerticalLayout() throws Exception {
        VaadinVerticalLayout expectedLayout = createMockAndExpectNew(VaadinVerticalLayout.class);

        // perform test
        replayAll();
        VerticalLayout createdLayout = guiFactory.createVerticalLayout();
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    public void testCreateTextField() throws Exception {
        VaadinTextField expectedTextField = createMockAndExpectNew(VaadinTextField.class);

        // perform test
        replayAll();
        TextField createdTextField = guiFactory.createTextField();
        verifyAll();

        assertEquals(expectedTextField, createdTextField);
    }
}
