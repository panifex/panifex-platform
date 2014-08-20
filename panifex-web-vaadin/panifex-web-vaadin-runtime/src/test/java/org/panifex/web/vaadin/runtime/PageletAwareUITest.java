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
package org.panifex.web.vaadin.runtime;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.vaadin.api.VaadinPagelet;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.util.CurrentInstance;

/**
 * Unit tests for {@link PageletAwareUI} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    CurrentInstance.class,
    VaadinPageletTracker.class})
public class PageletAwareUITest extends TestSupport {

    @Before
    public void setUp() {
        resetAll();

        mockStatic(CurrentInstance.class);
    }

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
    public void testInitNotMatchedPagelet() throws Exception {
        // variables
        String path = "/test";

        // mocks
        VaadinPageletTracker pageletTracker = createMock(VaadinPageletTracker.class);
        VaadinRequest request = createMock(VaadinRequest.class);

        // expect getting url path from request
        expect(request.getPathInfo()).andReturn(path);

        // expect matching path to pagelet
        expect(pageletTracker.matchPathToPagelet(path)).andReturn(null);

        // except returning HTTP 404
        VaadinResponse response = createMock(VaadinResponse.class);
        expect(CurrentInstance.get(VaadinResponse.class)).andReturn(response);
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");

        // perform test
        replayAll();
        PageletAwareUI ui = new PageletAwareUI(pageletTracker);
        ui.init(request);
        verifyAll();
    }
}
