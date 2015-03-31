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
package org.panifex.web.spi.security;

import org.junit.Test;
import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.tracker.GuiFactoryTracker;

/**
 * Unit tests for {@link LoginPagelet} class.
 */
public class LoginPageletTest extends TestSupport {

    // mocks
    private final BlueprintContainer blueprintContainer = createMock(BlueprintContainer.class);

    @Test(expected = NullPointerException.class)
    public void testConstructWithNullGuiFactoryTracker() {
        new SimpleLoginPagelet(blueprintContainer, null);
    }

    private class SimpleLoginPagelet extends LoginPagelet<Object> {

        public SimpleLoginPagelet(BlueprintContainer container,
                GuiFactoryTracker<Object> guiFactoryTracker) {
            super(container, guiFactoryTracker);
        }
    }
}
