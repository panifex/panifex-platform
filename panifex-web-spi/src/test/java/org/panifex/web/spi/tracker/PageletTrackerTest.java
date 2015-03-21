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
package org.panifex.web.spi.tracker;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.tracker.PageletTracker;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * Unit tests for the {@link PageletTracker} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PageletTracker.class)
public class PageletTrackerTest extends TestSupport {

    private TestPageletTracker tracker;

    static final String URL_PATTERN = "/*";

    @Before
    public void setUp() throws Exception {
        tracker = createStrictPartialMockAndInvokeDefaultConstructor(
                TestPageletTracker.class,
                "onPageletBinded",
                "onPageletUnbinded",
                "onPageletMappingBinded",
                "onPageletMappingUnbinded",
                "onUrlMappingAdded",
                "onUrlMappingRemoved");
    }

    @Test
    public void testBindNullPagelet() {
        bindPageletAndVerify(null);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindPageletWithNullName() {
        TestPagelet pageletMock = createPageletMock(null);

        bindPageletAndVerify(pageletMock);
    }

    @Test
    public void testBindValidPagelet() {
        TestPagelet pageletMock = createPageletMock(TestPagelet.PAGELET_NAME);

        // expect notifying that pagelet is binded
        tracker.onPageletBinded(pageletMock);

        bindPageletAndVerify(pageletMock);

        Collection<TestPagelet> pagelets = tracker.getPagelets();
        assertEquals(1, pagelets.size());
    }

    @Test
    public void testBindMappedPagelet() {
        TestPagelet pageletMock = createPageletMock(TestPagelet.PAGELET_NAME);

        // pagelet mapping already exists
        List<PageletMapping> pageletMappings = getActivePageletMappings();
        PageletMapping mapping = createPageletMappingMock(TestPagelet.PAGELET_NAME, URL_PATTERN);
        pageletMappings.add(mapping);

        // expect notifying that pagelet is binded
        tracker.onPageletBinded(pageletMock);

        // expect notifying that pagelet is mapped
        tracker.onUrlMappingAdded(URL_PATTERN, pageletMock);

        bindPageletAndVerify(pageletMock);

        Collection<TestPagelet> pagelets = tracker.getPagelets();
        assertEquals(1, pagelets.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindPageletWithTheSameNameTwice() {
        TestPagelet pageletMock = createPageletMock(TestPagelet.PAGELET_NAME, 2);

        Map<String, TestPagelet> pagelets = getActivePagelets();
        pagelets.put(TestPagelet.PAGELET_NAME, createPageletMock());

        bindPageletAndVerify(pageletMock);
    }

    @Test
    public void testUnbindNullPagelet() {
        unbindPageletAndVerify(null);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test
    public void testUnbindPageletWithoutName() {
        TestPagelet pageletMock = createPageletMock(null);

        unbindPageletAndVerify(pageletMock);
    }

    @Test
    public void testUnbindBindedPagelet() {
        TestPagelet pageletMock = createPageletMock(TestPagelet.PAGELET_NAME);

        // pagelet is already binded
        HashMap<String, TestPagelet> pagelets = getActivePagelets();
        pagelets.put(TestPagelet.PAGELET_NAME, pageletMock);

        // expect notifying that pagelet is unbinded
        tracker.onPageletUnbinded(pageletMock);

        assertFalse(tracker.getPagelets().isEmpty());

        unbindPageletAndVerify(pageletMock);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test
    public void testUnbindNotBindedPagelet() {
        TestPagelet pageletMock = createPageletMock(TestPagelet.PAGELET_NAME);

        unbindPageletAndVerify(pageletMock);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test
    public void testUnbindMappedPagelet() {
        TestPagelet pageletMock = createPageletMock(TestPagelet.PAGELET_NAME);

        // pagelet is already binded
        HashMap<String, TestPagelet> pagelets = getActivePagelets();
        pagelets.put(TestPagelet.PAGELET_NAME, pageletMock);

        // pagelet mapping already exists
        List<PageletMapping> pageletMappings = getActivePageletMappings();
        PageletMapping mapping = createPageletMappingMock(TestPagelet.PAGELET_NAME, URL_PATTERN);
        pageletMappings.add(mapping);

        // pagelet is mapped to url
        Map<String, TestPagelet> urlMappings = getActiveUrlMappings();
        urlMappings.put(URL_PATTERN, pageletMock);

        // expect notifying that pagelet is not mapped any more
        tracker.onUrlMappingRemoved(URL_PATTERN, pageletMock);

        // expect notifying that pagelet is unbinded
        tracker.onPageletUnbinded(pageletMock);

        unbindPageletAndVerify(pageletMock);
    }

    @Test
    public void testBindNullPageletMapping() {
        bindPageletMappingAndVerify(null);

        assertTrue(tracker.getPageletMappings().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindPageletMappingWithoutPageletName() {
        PageletMapping pageletMapping = createMock(PageletMapping.class);
        expect(pageletMapping.getPageletName()).andReturn(null);

        bindPageletMappingAndVerify(pageletMapping);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindPageletMappingWithoutUrlPatterns() {
        PageletMapping pageletMapping = createMock(PageletMapping.class);
        expect(pageletMapping.getPageletName()).andReturn(TestPagelet.PAGELET_NAME);
        expect(pageletMapping.getUrlPatterns()).andReturn(null);

        bindPageletMappingAndVerify(pageletMapping);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBindPageletMappingWithEmptyUrlPatternsList() {
        PageletMapping pageletMapping = createMock(PageletMapping.class);
        expect(pageletMapping.getPageletName()).andReturn(TestPagelet.PAGELET_NAME);
        expect(pageletMapping.getUrlPatterns()).andReturn(new String[0]);

        bindPageletMappingAndVerify(pageletMapping);
    }

    @Test
    public void testBindPageletMappingForUnbindedPagelet() {
        PageletMapping pageletMappingMock = createPageletMappingMock(
                TestPagelet.PAGELET_NAME, URL_PATTERN);

        // expect notifying that pagelet mapping is binded
        tracker.onPageletMappingBinded(pageletMappingMock);

        bindPageletMappingAndVerify(pageletMappingMock);

        List<PageletMapping> pageletMappings = tracker.getPageletMappings();
        assertEquals(1, pageletMappings.size());
    }

    @Test
    public void testBindPageletMappingForBindedPagelet() {
        PageletMapping pageletMappingMock = createPageletMappingMock(
                TestPagelet.PAGELET_NAME, URL_PATTERN);

        TestPagelet pageletMock = createPageletMock();

        HashMap<String, TestPagelet> pagelets = getActivePagelets();
        pagelets.put(TestPagelet.PAGELET_NAME, pageletMock);

        // expect notifying that pagelet mapping is binded
        tracker.onPageletMappingBinded(pageletMappingMock);

        // expect notifying that pagelet is mapped
        tracker.onUrlMappingAdded(URL_PATTERN, pageletMock);

        bindPageletMappingAndVerify(pageletMappingMock);

        List<PageletMapping> pageletMappings = tracker.getPageletMappings();
        assertEquals(1, pageletMappings.size());
    }

    @Test
    public void testUnbindNullPageletMapping() {
        unbindPageletMappingAndVerify(null);

        assertTrue(tracker.getPageletMappings().isEmpty());
    }

    @Test
    public void testUnbindBindedPageletMapping() {
        PageletMapping pageletMappingMock = createMock(PageletMapping.class);
        expect(pageletMappingMock.getPageletName()).andReturn(TestPagelet.PAGELET_NAME);

        List<PageletMapping> pageletMappings = getActivePageletMappings();
        pageletMappings.add(pageletMappingMock);

        // expect notifying that pagelet mapping is unbinded
        tracker.onPageletMappingUnbinded(pageletMappingMock);

        assertFalse(tracker.getPageletMappings().isEmpty());

        unbindPageletMappingAndVerify(pageletMappingMock);

        assertTrue(tracker.getPageletMappings().isEmpty());
    }

    @Test
    public void testUnbindNotBindedPageletMapping() {
        PageletMapping pageletMappingMock = createMock(PageletMapping.class);

        unbindPageletMappingAndVerify(pageletMappingMock);

        assertTrue(tracker.getPageletMappings().isEmpty());
    }

    private TestPagelet createPageletMock() {
        return createPageletMock(null, 0);
    }

    private TestPagelet createPageletMock(String pageletName) {
        return createPageletMock(pageletName, 1);
    }

    private TestPagelet createPageletMock(String pageletName, int times) {
        TestPagelet pageletMock = createMock(TestPagelet.class);
        if (times >= 1) {
            expect(pageletMock.getName()).andReturn(pageletName).times(times);
        }
        return pageletMock;
    }

    private PageletMapping createPageletMappingMock(String pageletName, String... urlPatterns) {
        PageletMapping mappingMock = createMock(PageletMapping.class);
        expect(mappingMock.getPageletName()).andReturn(pageletName);
        expect(mappingMock.getUrlPatterns()).andReturn(urlPatterns);
        return mappingMock;
    }

    private void bindPageletAndVerify(TestPagelet pagelet) {
        replayAll();
        tracker.bindPagelet(pagelet);
        verifyAll();
    }

    private void unbindPageletAndVerify(TestPagelet pagelet) {
        replayAll();
        tracker.unbindPagelet(pagelet);
        verifyAll();
    }

    private void bindPageletMappingAndVerify(PageletMapping pageletMapping) {
        replayAll();
        tracker.bindPageletMapping(pageletMapping);
        verifyAll();
    }

    private void unbindPageletMappingAndVerify(PageletMapping pageletMapping) {
        replayAll();
        tracker.unbindPageletMapping(pageletMapping);
        verifyAll();
    }

    private HashMap<String, TestPagelet> getActivePagelets() {
        return Whitebox.getInternalState(tracker, "pagelets");
    }

    private List<PageletMapping> getActivePageletMappings() {
        return Whitebox.getInternalState(tracker, "pageletMappings");
    }

    private Map<String, TestPagelet> getActiveUrlMappings() {
        return Whitebox.getInternalState(tracker, "urlMappings");
    }
}
