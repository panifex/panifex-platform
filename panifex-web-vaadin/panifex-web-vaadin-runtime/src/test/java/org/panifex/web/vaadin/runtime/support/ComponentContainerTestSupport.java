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
package org.panifex.web.vaadin.runtime.support;

import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.Container;
import org.panifex.web.vaadin.runtime.html.VaadinButton;
import org.panifex.web.vaadin.runtime.html.VaadinHtmlComponentUtil;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.vaadin.ui.ComponentContainer;

/**
 * Template test class for testing {@link Container} and
 * {@link ComponentContainer} subclasses.
 * <p>
 * This class is introduced because mismatched methods between Panifex's HtmlComponent and
 * Vaadin's ComponentContainer classes.
 */
@PrepareForTest(VaadinHtmlComponentUtil.class)
public abstract class ComponentContainerTestSupport<T extends Container> extends TestSupport {

    protected T component;

    protected abstract T constructComponent();

    @Before
    public final void setUpComponentContainerTestSupport() {
        mockStatic(VaadinHtmlComponentUtil.class);

        resetAll();

        component = constructComponent();
    }

    @Test
    public final void testAddHtmlComponent() {
        // mocks
        VaadinButton button = createMock(VaadinButton.class);

        // expect adding component
        VaadinHtmlComponentUtil.addComponentToContainer(button, (ComponentContainer) component);

        // perform test
        replayAll();
        component.addHtmlComponent(button);
        verifyAll();
    }

    @Test
    public final void testRemoveHtmlComponent() {
        // mocks
        VaadinButton button = createMock(VaadinButton.class);

        // expect removing component
        VaadinHtmlComponentUtil.removeComponentFromContainer(button, (ComponentContainer) component);

        // perform test
        replayAll();
        component.removeHtmlComponent(button);
        verifyAll();
    }
}
