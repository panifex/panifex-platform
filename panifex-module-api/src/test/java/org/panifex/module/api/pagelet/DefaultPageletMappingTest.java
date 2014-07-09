/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
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
package org.panifex.module.api.pagelet;

import org.junit.Test;
import org.panifex.module.api.pagelet.DefaultPageletMapping;
import org.panifex.module.api.pagelet.Pagelet;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link DefaultPageletMapping} class.
 */
public class DefaultPageletMappingTest extends TestSupport {

    final String[] urlPatterns = new String[] { "/*" };

    @Test
    public void testConstructWithPageletName() {
        String pageletName = "pageletName";

        PageletMapping mapping = new DefaultPageletMapping(pageletName, urlPatterns);

        assertEquals(pageletName, mapping.getPageletName());
        assertEquals(urlPatterns, mapping.getUrlPatterns());
    }

    @Test
    public void testConstructWithConcretePagelet() {
        Pagelet<?, ?> pageletMock = createMock(Pagelet.class);

        PageletMapping mapping = new DefaultPageletMapping(pageletMock, urlPatterns);

        assertEquals(pageletMock.getClass().getCanonicalName(), mapping.getPageletName());
        assertEquals(urlPatterns, mapping.getUrlPatterns());
    }
}
