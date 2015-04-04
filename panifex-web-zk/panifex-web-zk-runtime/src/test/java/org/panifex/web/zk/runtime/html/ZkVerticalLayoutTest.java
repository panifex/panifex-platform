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
package org.panifex.web.zk.runtime.html;

import org.junit.runner.RunWith;
import org.panifex.web.zk.runtime.support.LayoutTestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zul.Vlayout;

/**
 * Unit tests for {@link ZkVerticalLayout} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ZkVerticalLayout.class)
public class ZkVerticalLayoutTest extends LayoutTestSupport<ZkVerticalLayout> {

    private final String compId = getRandomChars(20);

    @Override
    public ZkVerticalLayout constructComponent() {
        suppress(constructor(Vlayout.class));
        suppress(method(Vlayout.class, "setId", String.class));
        return new ZkVerticalLayout(compId);
    }
}
