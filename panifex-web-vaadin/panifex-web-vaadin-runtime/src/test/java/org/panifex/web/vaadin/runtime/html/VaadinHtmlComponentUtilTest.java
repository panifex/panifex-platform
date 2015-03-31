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

import org.junit.Test;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.HtmlComponent;

import com.vaadin.ui.ComponentContainer;

/**
 * Unit tests for {@link VaadinHtmlComponentUtil} class.
 */
public class VaadinHtmlComponentUtilTest extends TestSupport {

    @Test
    public void testAddValidComponentToContainer() {
        // mocks
        ComponentContainer container = createMock(ComponentContainer.class);
        VaadinVerticalLayout component = createMock(VaadinVerticalLayout.class);

        // expect adding component
        container.addComponent(component);

        // perform test
        replayAll();
        VaadinHtmlComponentUtil.addComponentToContainer(component, container);
        verifyAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidComponentToContainer() {
        // mocks
        ComponentContainer container = createMock(ComponentContainer.class);
        HtmlComponent component = createMock(HtmlComponent.class);

        // perform test
        try {
            replayAll();
            VaadinHtmlComponentUtil.addComponentToContainer(component, container);
            fail("IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException is expected
            verifyAll();
            throw e;
        }
    }

    @Test
    public void testRemoveValidComponentFromContainer() {
        // mocks
        ComponentContainer container = createMock(ComponentContainer.class);
        VaadinVerticalLayout component = createMock(VaadinVerticalLayout.class);

        // expect removing component
        container.removeComponent(component);

        // perform test
        replayAll();
        VaadinHtmlComponentUtil.removeComponentFromContainer(component, container);
        verifyAll();
    }
}
