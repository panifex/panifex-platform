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

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.HtmlComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;

/**
 * Unit tests for {@link ZkHtmlComponentUtil} class.
 */
public class ZkHtmlComponentUtilTest extends TestSupport {

    @Before
    public void setUp() {
        resetAll();
    }

    @Test
    public void testAddValidComponentToContainer() {
        // mocks
        Component container = createMock(Component.class);
        ZkVerticalLayout component = createMock(ZkVerticalLayout.class);

        // expect adding component
        expect(container.appendChild(component)).andReturn(true);

        // perform test
        replayAll();
        ZkHtmlComponentUtil.addComponentToContainer(component, container);
        verifyAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddInvalidComponentToContainer() {
        // mocks
        Component container = createMock(Component.class);
        HtmlComponent component = createMock(HtmlComponent.class);

        // perform test
        try {
            replayAll();
            ZkHtmlComponentUtil.addComponentToContainer(component, container);
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
        Component container = createMock(Component.class);
        ZkVerticalLayout component = createMock(ZkVerticalLayout.class);

        // expect removing component
        expect(container.removeChild(component)).andReturn(true);

        // perform test
        replayAll();
        ZkHtmlComponentUtil.removeComponentFromContainer(component, container);
        verifyAll();
    }

    @Test
    public void testAddAlreadyAssignedStyleName() {
        addStyleName("AAA", "AAA AAA BBB AAA", "BBB AAA");
    }

    @Test
    public void testAddNotAssignedStyleName() {
        addStyleName("AAA", "BBB CCC", "BBB AAA CCC");
    }

    @Test
    public void testAddMultipleNotAssignedStyleNames() {
        addStyleName("AAA BBB", "CCC DDD", "BBB AAA DDD CCC");
    }

    @Test
    public void testAddStyleNameToNullActualStyles() {
        addStyleName("AAA", null, "AAA");
    }

    @Test
    public void testAddUnknownStyleName() {
        // add null style name
        addStyleName(null, null, null, false, false);

        // add empty style name
        addStyleName(StringUtils.EMPTY, null, null, false, false);

        // add blank style name
        addStyleName("   ", null, null, false, false);
    }

    @Test
    public void testRemoveAssignedStyleName() {
        removeStyleName("AAA", "BBB AAA CCC", "BBB CCC");
    }

    @Test
    public void testRemoveMultipleAssignedStyleNames() {
        removeStyleName("AAA BBB", "AAA BBB CCC", "CCC");
    }

    @Test
    public void testRemoveNotAssingedStyleName() {
        removeStyleName("AAA", "BBB CCC", "BBB CCC");
    }

    @Test
    public void testRemoveFromEmptyAssignedStyles() {
        removeStyleName("AAA", null, StringUtils.EMPTY);
    }

    @Test
    public void testRemoveUnknownStyleName() {
        // remove null style name
        removeStyleName(null, null, null, false, false);

        // remove empty style name
        removeStyleName(StringUtils.EMPTY, null, null, false, false);

        // remove blank style name
        removeStyleName("   ", null, null, false, false);
    }

    private void addStyleName(String styleName, String actualStyles, String expectedStyles) {
        addStyleName(styleName, actualStyles, expectedStyles, true, true);
    }

    private void addStyleName(
            String styleName,
            String actualStyles,
            String expectedStyles,
            boolean returnActualStyles,
            boolean assingNewStyles) {
        HtmlBasedComponent component = createMock(HtmlBasedComponent.class);

        // expect returning actual styles
        if (returnActualStyles) {
            expect(component.getSclass()).andReturn(actualStyles);
        }

        // expect assigning new styles
        if (assingNewStyles) {
            component.setSclass(expectedStyles);
        }

        // perform test
        replayAll();
        ZkHtmlComponentUtil.addStyleName(styleName, component);
        verifyAll();
    }

    private void removeStyleName(String styleName, String actualStyles, String expectedStyles) {
        removeStyleName(styleName, actualStyles, expectedStyles, true, true);
    }

    private void removeStyleName(
            String styleName,
            String actualStyles,
            String expectedStyles,
            boolean returnActualStyles,
            boolean assingNewStyles) {
        HtmlBasedComponent component = createMock(HtmlBasedComponent.class);

        // expect returning actual styles
        if (returnActualStyles) {
            expect(component.getSclass()).andReturn(actualStyles);
        }

        // expect assigning new styles
        if (assingNewStyles) {
            component.setSclass(expectedStyles);
        }

        // perform test
        replayAll();
        ZkHtmlComponentUtil.removeStyleName(styleName, component);
        verifyAll();
    }
}
