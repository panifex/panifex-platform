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
package org.panifex.web.zk.runtime.support;

import org.junit.Test;
import org.panifex.web.spi.html.Container;
import org.panifex.web.zk.runtime.html.ZkButton;
import org.panifex.web.zk.runtime.html.ZkHtmlComponentUtil;
import org.zkoss.zk.ui.Component;

/**
 * Template test class for testing {@link Container} and
 * {@link Layout} subclasses.
 */
public abstract class LayoutTestSupport<T extends Container>
    extends HtmlBasedComponentTestSupport<T> {

    @Test
    public final void testAddHtmlComponent() {
        // mocks
        ZkButton button = createMock(ZkButton.class);

        // expect adding component
        ZkHtmlComponentUtil.addComponentToContainer(button, (Component) component);

        // perform test
        replayAll();
        component.addHtmlComponent(button);
        verifyAll();
    }

    @Test
    public final void testRemoveHtmlComponent() {
        // mocks
        ZkButton button = createMock(ZkButton.class);

        // expect removing component
        ZkHtmlComponentUtil.removeComponentFromContainer(button, (Component) component);

        // perform test
        replayAll();
        component.removeHtmlComponent(button);
        verifyAll();
    }
}
