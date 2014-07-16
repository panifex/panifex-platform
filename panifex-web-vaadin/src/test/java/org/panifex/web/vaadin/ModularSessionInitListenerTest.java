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
import org.panifex.test.support.TestSupport;

import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinSession;

public class ModularSessionInitListenerTest extends TestSupport {

    private UIProvider uiProviderMock = createMock(UIProvider.class);

    private ModularSessionInitListener listener = new ModularSessionInitListener(uiProviderMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructWithNullUIProvider() {
        listener = new ModularSessionInitListener(null);
    }

    @Test
    public void testOnSessionInit() throws Exception {
        SessionInitEvent event = createMock(SessionInitEvent.class);

        // expect adding ui provider
        VaadinSession sessionMock = createMock(VaadinSession.class);
        expect(event.getSession()).andReturn(sessionMock);
        sessionMock.addUIProvider(uiProviderMock);

        replayAll();
        try {
            listener.sessionInit(event);
        } finally {
            verifyAll();
        }
    }
}
