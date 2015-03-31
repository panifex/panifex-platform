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
import org.zkoss.zul.Textbox;

/**
 * Unit tests for {@link ZkTextField} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    ZkHtmlComponentUtil.class,
    ZkTextField.class})
public class ZkTextFieldTest extends TestSupport {

    private ZkTextField textField;

    @Before
    public void setUp() {
        mockStatic(ZkHtmlComponentUtil.class);

        resetAll();

        // init layout
        suppress(defaultConstructorIn(Textbox.class));
        textField = new ZkTextField();
    }

    @Test
    public void testAddStyleName() {
        String styleName = "styleName";

        // expect adding style
        ZkHtmlComponentUtil.addStyleName(styleName, textField);

        // perform test
        replayAll();
        textField.addStyleName(styleName);
        verifyAll();
    }

    @Test
    public void testRemoveStyleName() {
        String styleName = "styleName";

        // expect remove style
        ZkHtmlComponentUtil.removeStyleName(styleName, textField);

        // perform test
        replayAll();
        textField.removeStyleName(styleName);
        verifyAll();
    }
}
