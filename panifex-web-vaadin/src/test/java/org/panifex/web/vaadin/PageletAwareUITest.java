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
package org.panifex.web.vaadin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.vaadin.api.VaadinPagelet;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vaadin.server.VaadinRequest;

/**
 * Unit tests for {@link PageletAwareUI} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VaadinPageletTracker.class)
public class PageletAwareUITest extends TestSupport {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullPageletTracker() {
        new PageletAwareUI(null);
    }

    @Test
    public void testInitMatchedPagelet() throws Exception {
        // variables
        String path = "/test";

        // mocks
        VaadinPageletTracker pageletTracker = createMock(VaadinPageletTracker.class);
        VaadinPagelet pagelet = createMock(VaadinPagelet.class);
        VaadinRequest request = createMock(VaadinRequest.class);

        // expect getting url path from request
        expect(request.getPathInfo()).andReturn(path);

        // expect matching path to pagelet
        expect(pageletTracker.matchPathToPagelet(path)).andReturn(pagelet);

        // expect servicing request
        pagelet.service(request);

        // perform test
        replayAll();
        PageletAwareUI ui = new PageletAwareUI(pageletTracker);
        ui.init(request);
        verifyAll();
    }

    @Test
    public void testInitNotMatchedPagelet() {
        // variables
        String path = "/test";

        // mocks
        VaadinPageletTracker pageletTracker = createMock(VaadinPageletTracker.class);
        VaadinRequest request = createMock(VaadinRequest.class);

        // expect getting url path from request
        expect(request.getPathInfo()).andReturn(path);

        // expect matching path to pagelet
        expect(pageletTracker.matchPathToPagelet(path)).andReturn(null);

        // perform test
        replayAll();
        PageletAwareUI ui = new PageletAwareUI(pageletTracker);
        ui.init(request);
        verifyAll();
    }
}
