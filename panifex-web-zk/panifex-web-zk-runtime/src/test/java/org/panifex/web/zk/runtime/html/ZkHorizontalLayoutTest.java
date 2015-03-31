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
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zul.Hlayout;

/**
 * Unit tests for {@link ZkHorizontalLayout} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    ZkHorizontalLayout.class,
    ZkHtmlComponentUtil.class})
public class ZkHorizontalLayoutTest extends TestSupport {

    private ZkHorizontalLayout layout;

    @Before
    public void setUp() {
        mockStatic(ZkHtmlComponentUtil.class);

        resetAll();

        // init layout
        suppress(constructor(Hlayout.class));
        layout = new ZkHorizontalLayout();
    }

    @Test
    public void testAddHtmlComponent() {
        // mocks
        ZkHorizontalLayout component = createMock(ZkHorizontalLayout.class);

        // expect adding component
        ZkHtmlComponentUtil.addComponentToContainer(component, layout);

        // perform test
        replayAll();
        layout.addHtmlComponent(component);
        verifyAll();
    }

    @Test
    public void testRemoveHtmlComponent() {
        // mocks
        ZkHorizontalLayout component = createMock(ZkHorizontalLayout.class);

        // expect removing component
        ZkHtmlComponentUtil.removeComponentFromContainer(component, layout);

        // perform test
        replayAll();
        layout.removeHtmlComponent(component);
        verifyAll();
    }

    @Test
    public void testAddStyleName() {
        String styleName = "styleName";

        // expect adding style
        ZkHtmlComponentUtil.addStyleName(styleName, layout);

        // perform test
        replayAll();
        layout.addStyleName(styleName);
        verifyAll();
    }

    @Test
    public void testRemoveStyleName() {
        String styleName = "styleName";

        // expect remove style
        ZkHtmlComponentUtil.removeStyleName(styleName, layout);

        // perform test
        replayAll();
        layout.removeStyleName(styleName);
        verifyAll();
    }
}
