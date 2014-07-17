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
package org.panifex.web.spi;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.PageletTracker;
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

    @Before
    public void setUp() throws Exception {
        tracker = createPartialMockAndInvokeDefaultConstructor(TestPageletTracker.class,
                "onPageletBinded",
                "onPageletUnbinded",
                "onPageletMappingBinded",
                "onPageletMappingUnbinded");
    }

    @Test
    public void testBindNullPagelet() {
        bindPageletAndVerify(null);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test
    public void testBindValidPagelet() {
        TestPagelet pageletMock = createMock(TestPagelet.class);

        // expect notifying that pagelet is binded
        tracker.onPageletBinded(pageletMock);

        bindPageletAndVerify(pageletMock);

        List<TestPagelet> pagelets = tracker.getPagelets();
        assertEquals(1, pagelets.size());
    }

    @Test
    public void testUnbindNullPagelet() {
        unbindPageletAndVerify(null);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test
    public void testUnbindBindedPagelet() {
        TestPagelet pageletMock = createMock(TestPagelet.class);

        List<TestPagelet> pagelets = Whitebox.getInternalState(tracker, "pagelets");
        pagelets.add(pageletMock);

        // expect notifying that pagelet is unbinded
        tracker.onPageletUnbinded(pageletMock);

        assertFalse(tracker.getPagelets().isEmpty());

        unbindPageletAndVerify(pageletMock);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    @Test
    public void testUnbindNotBindedPagelet() {
        TestPagelet pageletMock = createMock(TestPagelet.class);

        unbindPageletAndVerify(pageletMock);

        assertTrue(tracker.getPagelets().isEmpty());
    }

    private void bindPageletAndVerify(TestPagelet pagelet) {
        replayAll();
        try {
            tracker.bindPagelet(pagelet);
        } finally {
            verifyAll();
        }
    }

    private void unbindPageletAndVerify(TestPagelet pagelet) {
        replayAll();
        try {
            tracker.unbindPagelet(pagelet);
        } finally {
            verifyAll();
        }
    }

    @Test
    public void testBindNullPageletMapping() {
        bindPageletMappingAndVerify(null);

        assertTrue(tracker.getPageletMappings().isEmpty());
    }

    @Test
    public void testBindValidPageletMapping() {
        PageletMapping pageletMappingMock = createMock(PageletMapping.class);

        // expect notifying that pagelet mapping is binded
        tracker.onPageletMappingBinded(pageletMappingMock);

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

        List<PageletMapping> pageletMappings = Whitebox.getInternalState(tracker, "pageletMappings");
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

    private void bindPageletMappingAndVerify(PageletMapping pageletMapping) {
        replayAll();
        try {
            tracker.bindPageletMapping(pageletMapping);
        } finally {
            verifyAll();
        }
    }

    private void unbindPageletMappingAndVerify(PageletMapping pageletMapping) {
        replayAll();
        try {
            tracker.unbindPageletMapping(pageletMapping);
        } finally {
            verifyAll();
        }
    }
}
