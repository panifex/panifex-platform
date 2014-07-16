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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.vaadin.server.VaadinServletService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ModularVaadinServlet.class)
public class ModularVaadinServletTest extends TestSupport {

    // mocks
    private ModularSessionInitListener sessionInitListenerMock =
            createMock(ModularSessionInitListener.class);

    private ModularVaadinServlet servlet = new ModularVaadinServlet(sessionInitListenerMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullSessionInitListener() {
        servlet = new ModularVaadinServlet(null);
    }

    @Test
    public void testServletInitializedMethod() throws Exception {
        // expect adding session init listener
        VaadinServletService servletService = createMock(VaadinServletService.class);
        Whitebox.setInternalState(servlet, "servletService", servletService);
        servletService.addSessionInitListener(sessionInitListenerMock);

        replayAll();
        try {
            servlet.servletInitialized();
        } finally {
            verifyAll();
        }
    }
}
