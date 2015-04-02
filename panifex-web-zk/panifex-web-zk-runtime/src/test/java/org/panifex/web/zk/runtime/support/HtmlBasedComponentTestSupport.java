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

import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.zk.runtime.html.ZkHtmlComponentUtil;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.zkoss.zk.ui.HtmlBasedComponent;

/**
 * Template test class for testing {@link HtmlComponent} and
 * {@link HtmlBasedComponent} subclasses.
 * <p>
 * This class is introduced because mismatched methods between Panifex's HtmlComponent and ZKOSS's
 * HtmlBasedComponent classes.
 */
@PrepareForTest(ZkHtmlComponentUtil.class)
public abstract class HtmlBasedComponentTestSupport<T extends HtmlComponent> extends TestSupport {

    protected T component;

    protected abstract T constructComponent();

    @Before
    public final void setUpHtmlCompTestSupport() {
        mockStatic(ZkHtmlComponentUtil.class);

        resetAll();

        component = constructComponent();

        assertIsHtmlBasedComponentSubclass(component);
    }

    private T assertIsHtmlBasedComponentSubclass(T component) {
        if (!HtmlBasedComponent.class.isInstance(component)) {
            String msg = new StringBuilder("HtmlComponent must be instance of ").
                    append(HtmlBasedComponent.class.getCanonicalName()).
                    append(" class").
                    toString();
            throw new IllegalArgumentException(msg);
        } else {
            return component;
        }
    }

    /**
     * Tests the {@link HtmlComponent#addStyleName(String)} method.
     */
    @Test
    public final void testAddStyleName() {
        String styleName = getRandomChars(20);

        // expect adding style
        ZkHtmlComponentUtil.addStyleName(styleName, (HtmlBasedComponent) component);

        // perform test
        replayAll();
        component.addStyleName(styleName);
        verifyAll();
    }

    /**
     * Tedts the {@link HtmlComponent#removeStyleName(String)} method.
     */
    @Test
    public final void testRemoveStyleName() {
        String styleName = getRandomChars(20);

        // expect remove style
        ZkHtmlComponentUtil.removeStyleName(styleName, (HtmlBasedComponent) component);

        // perform test
        replayAll();
        component.removeStyleName(styleName);
        verifyAll();
    }
}
