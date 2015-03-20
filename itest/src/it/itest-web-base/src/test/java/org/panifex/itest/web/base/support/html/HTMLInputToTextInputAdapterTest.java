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
import org.panifex.test.support.TestSupport;

import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

/**
 * Unit tests for {@link HTMLInputToTextInputAdapter} class.
 */
public class HTMLInputToTextInputAdapterTest extends TestSupport {

    // mocks
    private final HTMLInputElement inputElementMock = createMock(HTMLInputElement.class);

    private final HTMLInputToTextInputAdapter adapter = new HTMLInputToTextInputAdapter(inputElementMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test
    public void testGetValueAttribute() {
        String expectedValue = getRandomChars(20);

        // expect getting attribute value
        expect(inputElementMock.getValue()).andReturn(expectedValue);

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
        inputElementMock.setValue(newValue);

        // perform test
        replayAll();
        adapter.setValueAttribute(newValue);
        verifyAll();
    }
}
