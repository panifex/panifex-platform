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
package org.panifex.test.support;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.web.service.spi.ServletListener;
import org.ops4j.pax.web.service.spi.WebListener;
import org.osgi.framework.BundleContext;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IWebTestSupport.class)
public class IWebTestSupportTest extends TestSupport {

    private IWebTestSupport testSupport = new SimpleWebTestSupport();

    // mocks
    private BundleContext bundleContext = createMock(BundleContext.class);

    @Before
    public void setUp() {
        testSupport.bundleContext = bundleContext;
    }

    @Test
    public void testRegisterServletListenerWithoutName() throws Exception {
        // expect registering servlet listener
        ServletListener servletListener = createMockAndExpectNew(ServletListenerImpl.class);
        expect(bundleContext.registerService(ServletListener.class, servletListener, null)).andReturn(null);

        replayAll();
        testSupport.initServletListener();
        verifyAll();
    }

    @Test
    public void testRegisterServletListenerWithName() throws Exception {
        String servletName = "servletName";

        // expect registering servlet listener
        ServletListener servletListener = createMockAndExpectNew(ServletListenerImpl.class, servletName);
        expect(bundleContext.registerService(ServletListener.class, servletListener, null)).andReturn(null);

        replayAll();
        testSupport.initServletListener(servletName);
        verifyAll();
    }

    @Test(expected = IllegalStateException.class)
    public void testRegisterServletListenerTwice() throws Exception {
        // expect registering servlet listener
        ServletListener servletListener = createMockAndExpectNew(ServletListenerImpl.class);
        expect(bundleContext.registerService(ServletListener.class, servletListener, null)).andReturn(null);

        replayAll();
        try {
            testSupport.initServletListener();
        } catch (Exception e) {
            fail("first registration should pass ok");
        }
        verifyAll();

        testSupport.initServletListener();
    }

    @Test
    public void testRegisterWebListener() throws Exception {
        // expect registering web listener
        WebListener servletListener = createMockAndExpectNew(WebListenerImpl.class);
        expect(bundleContext.registerService(WebListener.class, servletListener, null)).andReturn(null);

        replayAll();
        testSupport.initWebListener();
        verifyAll();
    }

    @Test(expected = IllegalStateException.class)
    public void testRegisterWebListenerTwice() throws Exception {
        // expect registering web listener
        WebListener servletListener = createMockAndExpectNew(WebListenerImpl.class);
        expect(bundleContext.registerService(WebListener.class, servletListener, null)).andReturn(null);

        replayAll();
        try {
            testSupport.initWebListener();
        } catch (Exception e) {
            fail("first registration should pass ok");
        }
        verifyAll();

        // try to register web listener twice
        testSupport.initWebListener();
    }
}
