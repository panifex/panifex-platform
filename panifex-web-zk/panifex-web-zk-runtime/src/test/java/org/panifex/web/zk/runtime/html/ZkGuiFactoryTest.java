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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.Button;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
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
public class ZkGuiFactoryTest extends TestSupport {

    private final GuiFactory<Page> guiFactory = new ZkGuiFactory();

    @Test
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
    public void testInitViewModelBinding() throws Exception {
        // mocks
        ZkHorizontalLayout layout = createMock(ZkHorizontalLayout.class);
        Object viewModel = createMock(Object.class);

        // expect creating binder
        DefaultBinder binder = createMockAndExpectNew(DefaultBinder.class);
        binder.init(layout, viewModel, null);
        expect(layout.setAttribute(ZkGuiFactory.VM_BIND_ID, viewModel)).andReturn(createMock(Object.class));

        // perform test
        replayAll();
        guiFactory.initViewModelBinding(viewModel, layout);
        verifyAll();
    }

    @Test
    public void testCreateButton() throws Exception {
        ZkButton expectedButton = createMockAndExpectNew(ZkButton.class);

        // perform test
        replayAll();
        Button createdButton = guiFactory.createButton();
        verifyAll();

        assertEquals(expectedButton, createdButton);
    }

    @Test
    public void testCreateHorizontalLayout() throws Exception {
        ZkHorizontalLayout expectedLayout = createMockAndExpectNew(ZkHorizontalLayout.class);

        // perform test
        replayAll();
        HorizontalLayout createdLayout = guiFactory.createHorizontalLayout();
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    public void testCreateVerticalLayout() throws Exception {
        ZkVerticalLayout expectedLayout = createMockAndExpectNew(ZkVerticalLayout.class);

        // perform test
        replayAll();
        VerticalLayout createdLayout = guiFactory.createVerticalLayout();
        verifyAll();

        assertEquals(expectedLayout, createdLayout);
    }

    @Test
    public void testCreateTextField() throws Exception {
        ZkTextField expectedTextField = createMockAndExpectNew(ZkTextField.class);

        // perform test
        replayAll();
        TextField createdTextField = guiFactory.createTextField();
        verifyAll();

        assertEquals(expectedTextField, createdTextField);
    }
}
