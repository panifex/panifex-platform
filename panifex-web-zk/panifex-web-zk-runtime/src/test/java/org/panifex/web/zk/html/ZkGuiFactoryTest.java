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
package org.panifex.web.zk.html;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.zk.runtime.html.ZkGuiFactory;
import org.panifex.web.zk.runtime.html.ZkHorizontalLayout;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for {@link ZkGuiFactory} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ZkGuiFactory.class)
public class ZkGuiFactoryTest extends TestSupport {

    private final GuiFactory guiFactory = new ZkGuiFactory();

    @Test
    public void testCreateHorizontalLayout() throws Exception {
        ZkHorizontalLayout horizontalLayout = createMockAndExpectNew(ZkHorizontalLayout.class);

        // perform test
        replayAll();
        HorizontalLayout createdHorizontalLayout = guiFactory.createHorizontalLayout();
        verifyAll();

        assertEquals(horizontalLayout, createdHorizontalLayout);

    }
}
