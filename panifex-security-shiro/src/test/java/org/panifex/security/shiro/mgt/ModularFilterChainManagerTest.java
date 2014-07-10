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
package org.panifex.security.shiro.mgt;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.security.SecFilter;
import org.panifex.module.api.security.SecFilterMapping;
import org.panifex.security.shiro.mgt.ModularFilterChainManager;
import org.panifex.security.shiro.mgt.SecFilterMappingMapImpl;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for the {@link ModularFilterChainManager} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ModularFilterChainManager.class)
public class ModularFilterChainManagerTest extends TestSupport {

    private ModularFilterChainManager manager;

    // mocks
    private SecFilterMappingMapImpl filterMappingMapMock;

    // variables used in wide tests
    final String filterName = "filterName";
    final String url1 = "/url1";
    final String url2 = "/url2";

    @Before
    public void setUp() throws Exception {
        filterMappingMapMock = createMockAndExpectNew(SecFilterMappingMapImpl.class);

        replayAll();
        manager = new ModularFilterChainManager(new SecFilterMappingMapImpl());
        verifyAll();
        resetAll();
    }

    @Test
    public void testBindNullFilter() {
        bindFilterAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindFilterWithNullName() {
        SecFilter filterMock = createMock(SecFilter.class);
        expect(filterMock.getFilterName()).andReturn(null);

        bindFilterAndVerify(filterMock);
    }

    /**
     * Binds the {@link SecFilter} that is not already mapped to any url.
     * <p>
     * The {@link SecFilter} should be added to the filter's map only.
     */
    @Test
    public void testBindNotMappedFilter() {
        bindFilter(false);
    }

    /**
     * Binds the {@link SecFilter} that is already mapped to an URL.
     * <p>
     * The {@link SecFilter} should be added to the filter's list and it should be associated
     * to the filter chains.
     */
    @Test
    public void testBindAlreadyMappedFilter() {
        bindFilter(true);
    }

    @Test
    public void testUnbindNullFilter() {
        unbindFilterAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbindFilterWithNullFilterName() {
        SecFilter filterMock = createMock(SecFilter.class);
        expect(filterMock.getFilterName()).andReturn(null);

        unbindFilterAndVerify(filterMock);
    }

    /**
     * Unbinds the {@link SecFilter} which is not mapped to any URL.
     * <p>
     * The filter should be removed from the filter's map only.
     */
    @Test
    public void testUnbindNotMappedFilter() {
        unbindFilter(false);
    }

    /**
     * Unbinds the {@link SecFilter} which is mapped to the URL.
     * <p>
     * The should be removed from the filter's map and the filter should be removed from
     * the filter chains.
     */
    @Test
    public void testUnbindMappedFilter() {
        unbindFilter(true);
    }

    @Test
    public void testBindNullFilterMapping() {
        bindFilterMappingAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindFilterMappingWithNullFilterName() {
        SecFilterMapping filterMappingMock = createMock(SecFilterMapping.class);
        expect(filterMappingMock.getFilterName()).andReturn(null);

        bindFilterMappingAndVerify(filterMappingMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindFilterMappingWithNullUrl() {
        SecFilterMapping filterMappingMock = createMock(SecFilterMapping.class);
        expect(filterMappingMock.getFilterName()).andReturn(filterName);
        expect(filterMappingMock.getUrl()).andReturn(null);

        bindFilterMappingAndVerify(filterMappingMock);
    }

    /**
     * Binds the {@link SecFilterMapping} that maps the {@link SecFilter} which
     * is not already binded to the {@link ModularFilterChainManager}.
     * <p>
     * The mapping should be added to the mapping map and the filter should not be added
     * to the filter chains.
     */
    @Test
    public void testBindNewlyFilterMappingWithoutAssociateFilter() {
        bindFilterMapping(false, false, false);
    }

    /**
     * Binds the {@link SecFilterMapping} that maps the {@link SecFilter} which is binded
     * to the {@link ModularFilterChainManager}.
     * <p>
     * The mapping should be added to the mapping map and the filter should be added to
     * the filter chains.
     */
    @Test
    public void testBindNewlyFilterMappingWithAssociateFilter() {
        bindFilterMapping(true, false, true);
    }

    /**
     * Binds the {@link SecFilterMapping} that maps the {@link SecFilter} which is already
     * registered to the same URL in the filter chains.
     * <p>
     * The mapping should be added to the mapping map, but the filter should not be added
     * to the filter chains because the filter can only once be assigned to the same URL.
     */
    @Test
    public void testBindFilterMappingWithAlreadyMappedFilterToSameUrl() {
        bindFilterMapping(true, true, true);
    }

    /**
     * Binds the {@link SecFilterMapping} that maps the {@link SecFilter} which is already
     * registered to the different URL from the newly URL in the filter chains.
     * <p>
     * The mapping should be added to the mapping map and the filter should be added to
     * the filter chains to the newly URL.
     */
    @Test
    public void testBindFilterMappingWithAlreadyMappedFilterToDifferentUrl() {
        bindFilterMapping(true, true, false);
    }

    @Test
    public void testUnbindNullFilterMapping() {
        unbindFilterMappingAndVerify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbindFilterMappingWithNullName() {
        SecFilterMapping filterMapping = createMock(SecFilterMapping.class);
        expect(filterMapping.getFilterName()).andReturn(null);

        unbindFilterMappingAndVerify(filterMapping);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnbindFilterMappingWithNullUrl() {
        SecFilterMapping filterMapping = createMock(SecFilterMapping.class);
        expect(filterMapping.getFilterName()).andReturn(filterName);
        expect(filterMapping.getUrl()).andReturn(null);

        unbindFilterMappingAndVerify(filterMapping);
    }

    /**
     * Unbinds the {@link SecFilterMapping} of {@link SecFilter} that is not binded to the
     * {@link ModularFilterChainManager}.
     * <p>
     * The mapping should be removed from the mapping map.
     */
    @Test
    public void testUnbindFilterMappingWithoutBindedFilter() {
        unbindFilterMapping(false, false);
    }

    /**
     * Unbinds the {@link SecFilterMapping} of the already registered {@link SecFilter}.
     * <p>
     * The {@link SecFilter} should be removed from the filter chains.
     */
    @Test
    public void testUnbindFilterMappingOfRegisteredFilter() {
        unbindFilterMapping(true, false);
    }

    /**
     * Unbinds the {@link SecFilterMapping} of the {@link SecFilter} which is twice
     * registered to the same URL.
     * <p>
     * The {@link SecFilter} should not be removed from the filter chains, because the
     * other mapping remains to the same URL.
     */
    @Test
    public void testUnbindFilterMappingOfTwiceRegisteredFilter() {
        unbindFilterMapping(true, true);
    }

    /**
     * Binds the {@link SecFilter} to the {@link ModularFilterChainManager}.
     *
     * @param mappingExists
     *      is the {@link SecFilter} mapped to some URL
     */
    private void bindFilter(boolean mappingExists) {
        // filter to be binded
        SecFilter filterMock = createMock(SecFilter.class);
        expect(filterMock.getFilterName()).andReturn(filterName).atLeastOnce();

        // set of URLs in which filter is mapped
        Set<String> urls = null;
        if (mappingExists) {
            urls = new HashSet<>();
            urls.add(url1);
        }

        // filter mapping map returns set of URLs in which filter is mapped
        expect(filterMappingMapMock.getMappedUrls(filterName)).andReturn(urls);

        bindFilterAndVerify(filterMock);

        assertEquals(filterMock, manager.getFilter(filterName));

        if (mappingExists) {
            // if filter is mapped, it should be added to filter chain
            NamedFilterList filterList = manager.getChain(url1);
            assertEquals(1, filterList.size());
            assertEquals(filterMock, filterList.get(0));
        } else {
            assertTrue(manager.getFilterChains().isEmpty());
        }
    }

    private void bindFilterAndVerify(SecFilter filter) {
        replayAll();
        try {
            manager.bindFilter(filter);
        } finally {
            verifyAll();
        }
    }

    /**
     * Unbinds the {@link SecFilter} to the {@link ModularFilterChainManager}.
     *
     * @param mappingExists
     *      is the {@link SecFilter} mapped to some URL
     */
    private void unbindFilter(boolean mappingExists) {
        // filter to be unbinded
        SecFilter filterMock = createMock(SecFilter.class);
        expect(filterMock.getFilterName()).andReturn(filterName).atLeastOnce();

        Set<String> urls = null;
        if (mappingExists) {
            urls = new HashSet<>();
            urls.add(url1);
        }
        expect(filterMappingMapMock.getMappedUrls(filterName)).andReturn(urls);

        unbindFilterAndVerify(filterMock);

        assertTrue(manager.getFilterChains().isEmpty());
    }

    private void unbindFilterAndVerify(SecFilter filter) {
        replayAll();
        try {
            manager.unbindFilter(filter);
        } finally {
            verifyAll();
        }
    }

    /**
     * Binds the {@link SecFilterMapping} to the {@link ModularFilterChainManager}.
     *
     * @param filterExists
     *      is the associated {@link SecFilter} is already binded to the manager
     * @param filterAlreadyMapped
     *      is the {@link SecFilterMapping} of the same {@link SecFilter} already exists
     * @param filterMappedToSameUrl
     *      is the existed {@link SecFilterMapping} of the same {@link SecFilter} is already
     *      mapped to the same URL or to the other one. It is only assumed if the
     *      <code>filterAlreadyMapped</code> is true.
     */
    private void bindFilterMapping(
            boolean filterExists,
            boolean filterAlreadyMapped,
            boolean filterMappedToSameUrl) {
        String url = filterMappedToSameUrl ? url1 : url2;

        // filter mapping to be binded
        SecFilterMapping filterMappingMock = createMock(SecFilterMapping.class);
        expect(filterMappingMock.getFilterName()).andReturn(filterName).atLeastOnce();
        expect(filterMappingMock.getUrl()).andReturn(url).atLeastOnce();

        SecFilter filterMock = null;
        if (filterExists) {
            // already mapped filter
            filterMock = createMock(SecFilter.class);
            manager.addFilter(filterName, filterMock);
            manager.addToChain(url1, filterName);

            expect(filterMappingMapMock.isFilterMapped(filterName, url))
                .andReturn(filterAlreadyMapped && filterMappedToSameUrl);
        }

        // expect adding new mapping to filter mapping map
        filterMappingMapMock.add(filterMappingMock);

        bindFilterMappingAndVerify(filterMappingMock);

        if (filterExists) {
            int registeredFilters = filterAlreadyMapped && !filterMappedToSameUrl ? 2 : 1;
            assertEquals(registeredFilters, manager.getFilterChains().size());
            assertTrue(manager.getFilterChains().containsKey(url));
            assertEquals(filterMock, manager.getChain(url).get(0));
        } else {
            assertTrue(manager.getFilterChains().isEmpty());
        }
    }

    private void bindFilterMappingAndVerify(SecFilterMapping filterMapping) {
        replayAll();
        try {
            manager.bindFilterMapping(filterMapping);
        } finally {
            verifyAll();
        }
    }

    /**
     * Unbinds the {@link SecFilterMapping} from the {@link ModularFilterChainManager}.
     *
     * @param filterExists
     *      is the associated {@link SecFilter} is already binded to the manager
     * @param filterMappedTwice
     *      is the two {@link SecFilterMapping} of the same {@link SecFilter} already exists
     */
    private void unbindFilterMapping(boolean filterExists, boolean filterMappedTwice) {
        // filter mapping to be binded
        SecFilterMapping filterMappingMock = createMock(SecFilterMapping.class);
        expect(filterMappingMock.getFilterName()).andReturn(filterName).atLeastOnce();
        expect(filterMappingMock.getUrl()).andReturn(url1).atLeastOnce();

        SecFilter filterMock = null;
        if (filterExists) {
            // already mapped filter
            filterMock = createMock(SecFilter.class);
            manager.addFilter(filterName, filterMock);
            manager.addToChain(url1, filterName);

            expect(filterMappingMapMock.isFilterMapped(filterName, url1))
                .andReturn(filterMappedTwice);
        }

        // expect removing mapping from filter mapping map
        filterMappingMapMock.remove(filterMappingMock);

        unbindFilterMappingAndVerify(filterMappingMock);

        if (filterExists && filterMappedTwice) {
            assertEquals(1, manager.getFilterChains().size());
            assertTrue(manager.getFilterChains().containsKey(url1));
            assertEquals(filterMock, manager.getChain(url1).get(0));
        } else {
            assertTrue(manager.getFilterChains().isEmpty());
        }
    }

    private void unbindFilterMappingAndVerify(SecFilterMapping filterMapping) {
        replayAll();
        try {
            manager.unbindFilterMapping(filterMapping);
        } finally {
            verifyAll();
        }
    }
}
