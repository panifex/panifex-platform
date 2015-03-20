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

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;

/**
 * Unit tests for {@link HtmlDivisionToButtonAdapter} class.
 */
public class HtmlDivisionToButtonAdapterTest extends TestSupport {

    // mocks
    private final HtmlDivision htmlDivisionMock = createMock(HtmlDivision.class);

    private final HtmlDivisionToButtonAdapter adapter =
            new HtmlDivisionToButtonAdapter(htmlDivisionMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test
    public void testClick() throws Exception {
        Page page = createMock(Page.class); // html division returns page, it is not used by adapter

        // expect double click
        expect(htmlDivisionMock.dblClick()).andReturn(page);

        // perform test
        replayAll();
        adapter.click();
        verifyAll();
    }
}
