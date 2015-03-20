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

import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;

/**
 * Unit tests for {@link HTMLButtonElement} class.
 */
public class HTMLButtonToButtonAdapterTest extends TestSupport {

    // mocks
    private final HTMLButtonElement buttonElement = createMock(HTMLButtonElement.class);

    private final HTMLButtonToButtonAdapter adapter =
            new HTMLButtonToButtonAdapter(buttonElement);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test
    public void testClick() throws Exception {
        // expect click
        buttonElement.click();

        // perform test
        replayAll();
        adapter.click();
        verifyAll();
    }
}
