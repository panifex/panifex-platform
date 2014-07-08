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
package org.panifex.module.api.security;

import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.junit.Test;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link DefaultSecFilterMapping} class.
 */
public class DefaultSecFilterMappingTest extends TestSupport {

    final String filterName = "filter";
    final String url = "/url";

    @Test
    public void testConstructMappingOfSpecifiedFilterName() {
        SecFilterMapping mapping = new DefaultSecFilterMapping(url, filterName);

        assertEquals(url, mapping.getUrl());
        assertEquals(filterName, mapping.getFilterName());
    }

    @Test
    public void testConstructMappingOfConcreateFilter() {
        SecFilter filter = createMock(SecFilter.class);
        expect(filter.getFilterName()).andReturn(filterName);

        replayAll();
        SecFilterMapping mapping = new DefaultSecFilterMapping(url, filter);
        verifyAll();

        assertEquals(url, mapping.getUrl());
        assertEquals(filterName, mapping.getFilterName());
    }

    @Test
    public void testConstructMappingOfDefaultShiroFilter() {
        DefaultFilter defaultFilter = DefaultFilter.anon;

        replayAll();
        SecFilterMapping mapping = new DefaultSecFilterMapping(url, defaultFilter);
        verifyAll();

        assertEquals(url, mapping.getUrl());
        assertEquals(defaultFilter.toString(), mapping.getFilterName());
    }
}
