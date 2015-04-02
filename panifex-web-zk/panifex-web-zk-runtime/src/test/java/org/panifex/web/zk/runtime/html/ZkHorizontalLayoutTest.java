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
import org.panifex.web.zk.runtime.support.HtmlBasedComponentTestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zul.Hlayout;

/**
 * Unit tests for {@link ZkHorizontalLayout} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ZkHorizontalLayout.class)
public class ZkHorizontalLayoutTest extends HtmlBasedComponentTestSupport<ZkHorizontalLayout> {

    @Override
    public ZkHorizontalLayout constructComponent() {
        suppress(constructor(Hlayout.class));
        return new ZkHorizontalLayout();
    }

    @Test
    public void testAddHtmlComponent() {
        // mocks
        ZkButton button = createMock(ZkButton.class);

        // expect adding component
        ZkHtmlComponentUtil.addComponentToContainer(button, component);

        // perform test
        replayAll();
        component.addHtmlComponent(button);
        verifyAll();
    }

    @Test
    public void testRemoveHtmlComponent() {
        // mocks
        ZkButton button = createMock(ZkButton.class);

        // expect removing component
        ZkHtmlComponentUtil.removeComponentFromContainer(button, component);

        // perform test
        replayAll();
        component.removeHtmlComponent(button);
        verifyAll();
    }
}
