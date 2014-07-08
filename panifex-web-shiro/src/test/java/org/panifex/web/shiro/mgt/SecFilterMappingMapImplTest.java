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
package org.panifex.web.shiro.mgt;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.security.SecFilterMapping;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link SecFilterMappingMapImpl} class.
 */
public class SecFilterMappingMapImplTest extends TestSupport {

    private SecFilterMappingMap map;

    // variables used in wide test cases
    final String filterName = "filterName";
    final String url1 = "/url1";
    final String url2 = "/url2";

    @Before
    public void setUp() {
        map = new SecFilterMappingMapImpl();

        resetAll();
    }

    @Test
    public void testAddTwoMappingsToSameUrlAndRemoveFirst() {
        addTwoMappings(url1, url1);
    }

    @Test
    public void testAddTwoMappingsToDifferentUrlsAndRemoveFirst() {
        addTwoMappings(url1, url2);
    }

    private void addTwoMappings(String url1, String url2) {
        SecFilterMapping mapping1 = createMappingMock(url1);
        SecFilterMapping mapping2 = createMappingMock(url2);

        // add two mappings
        replayAll();
        map.add(mapping1);
        map.add(mapping2);

        Set<String> mappedUrls = map.getMappedUrls(filterName);

        int numOfMappedUrls = url1.equals(url2) ? 1 : 2;
        assertEquals(numOfMappedUrls, mappedUrls.size());

        assertTrue(mappedUrls.contains(url1));
        assertTrue(mappedUrls.contains(url2));

        assertTrue(map.isFilterMapped(filterName, url1));
        assertTrue(map.isFilterMapped(filterName, url2));

        // remove first mapping
        map.remove(mapping1);
        verifyAll();

        assertEquals(1, mappedUrls.size());

        assertTrue(mappedUrls.contains(url2));
        assertTrue(map.isFilterMapped(filterName, url2));

        if (url1.equals(url2)) {
            assertTrue(mappedUrls.contains(url1));
            assertTrue(map.isFilterMapped(filterName, url1));
        } else {
            assertFalse(mappedUrls.contains(url1));
            assertFalse(map.isFilterMapped(filterName, url1));
        }
    }

    private SecFilterMapping createMappingMock(String url) {
        SecFilterMapping mappingMock = createMock(SecFilterMapping.class);
        expect(mappingMock.getFilterName()).andReturn(filterName).atLeastOnce();
        expect(mappingMock.getUrl()).andReturn(url).atLeastOnce();
        return mappingMock;
    }
}
