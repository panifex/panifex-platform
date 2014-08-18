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
package org.panifex.web.zk;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.zk.api.ZkPagelet;
import org.panifex.test.support.TestSupport;
import org.panifex.web.zk.runtime.ZkPageletTracker;
import org.panifex.web.zk.runtime.ZkRichletDispatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Page;

/**
 * Unit tests for the {@link ZkRichletDispatcher} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ZkPageletTracker.class)
public class ZkRichletDispatcherTest extends TestSupport {

    private final String path = "/path";

    // mocks
    private ZkPageletTracker pageletTracker = createMock(ZkPageletTracker.class);

    /**
     * Instance to be tested.
     */
    private ZkRichletDispatcher dispatcher = new ZkRichletDispatcher(pageletTracker);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullPageletTracker() {
        new ZkRichletDispatcher(null);
    }

    @Test
    public void testServiceMatchedPagelet() throws Exception {
        // mocks
        Page request = createRequestMock();
        ZkPagelet pagelet = createMock(ZkPagelet.class);

        // expect matching path to pagelet
        expect(pageletTracker.matchPathToPagelet(path)).andReturn(pagelet);

        // expect servicing request
        pagelet.service(request);

        // perform test
        replayAll();
        dispatcher.service(request);
        verifyAll();
    }

    @Test(expected = Exception.class)
    public void testServiceNotMatchedPagelet() throws Exception {
        // mocks
        Page request = createRequestMock();

        // expect matching path to unknown pagelet
        expect(pageletTracker.matchPathToPagelet(path)).andReturn(null);

        // perform test
        replayAll();
        dispatcher.service(request);
        verifyAll();
    }

    private Page createRequestMock() {
        Page request = createMock(Page.class);

        // expect getting request's path
        expect(request.getRequestPath()).andReturn(path);

        return request;
    }
}
