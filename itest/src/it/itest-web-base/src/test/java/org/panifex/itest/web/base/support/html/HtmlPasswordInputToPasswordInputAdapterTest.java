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
package org.panifex.itest.web.base.support.html;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;

/**
 * Unit tests for {@link HtmlPasswordInputToPasswordInputAdapter} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(HtmlPasswordInput.class)
public class HtmlPasswordInputToPasswordInputAdapterTest extends TestSupport {

    // mocks
    private final HtmlPasswordInput passwordInputMock = createMock(HtmlPasswordInput.class);

    private final HtmlPasswordInputToPasswordInputAdapter adapter =
            new HtmlPasswordInputToPasswordInputAdapter(passwordInputMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test(expected = NullPointerException.class)
    public void testConstructWithNullPasswordInput() {
        new HtmlPasswordInputToPasswordInputAdapter(null);
    }

    @Test
    public void testGetValueAttribute() {
        String expectedValue = getRandomChars(20);

        // expect getting attribute value
        expect(passwordInputMock.getValueAttribute()).andReturn(expectedValue);

        // perform test
        replayAll();
        String actualValue = adapter.getValueAttribute();
        verifyAll();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void testSetValueAttribute() {
        String newValue = getRandomChars(20);

        // expect setting new attribute value
        expect(passwordInputMock.setValueAttribute(newValue)).andReturn(createMock(Page.class));

        // perform test
        replayAll();
        adapter.setValueAttribute(newValue);
        verifyAll();
    }
}
