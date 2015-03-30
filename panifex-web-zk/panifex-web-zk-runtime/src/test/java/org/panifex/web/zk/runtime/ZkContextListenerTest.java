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
package org.panifex.web.zk.runtime;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.util.Configuration;

/**
 * Unit tests for the {@link ZkContextListener} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    Configuration.class,
    WebManager.class})
public class ZkContextListenerTest extends TestSupport {

    private final String dispatcherName = ZkRichletDispatcher.class.getCanonicalName();

    @Before
    public void setUp() {
        resetAll();
        mockStatic(WebManager.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullZkRichletDispatcher() {
        new ZkContextListener(null);
    }

    @Test
    public void testOnContextInitialized() {
        // mocks
        Configuration configuration = createMock(Configuration.class);
        ServletContext servletContext = createMock(ServletContext.class);
        ServletContextEvent servletContextEvent = createMock(ServletContextEvent.class);
        ZkRichletDispatcher dispatcher = createDispatcherMock();
        WebApp webApp = createMock(WebApp.class);

        // expect getting servlet context
        expect(servletContextEvent.getServletContext()).andReturn(servletContext);

        // expect getting configuration from web manager
        expect(WebManager.getWebApp(servletContext)).andReturn(webApp);
        expect(webApp.getConfiguration()).andReturn(configuration);

        // expect registering dispatcher
        expect(configuration.addRichlet(dispatcherName, dispatcher)).andReturn(null);
        configuration.addRichletMapping(dispatcherName, "/*");

        // perform test
        replayAll();
        ZkContextListener listener = new ZkContextListener(dispatcher);
        listener.contextInitialized(servletContextEvent);
        verifyAll();
    }

    private ZkRichletDispatcher createDispatcherMock() {
        ZkRichletDispatcher dispatcher = createMock(ZkRichletDispatcher.class);

        // expect getting name
        expect(dispatcher.getName()).
            andReturn(dispatcherName).
            anyTimes();

        return dispatcher;
    }
}
