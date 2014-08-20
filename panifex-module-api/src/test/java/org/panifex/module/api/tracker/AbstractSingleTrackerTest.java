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
package org.panifex.module.api.tracker;

import org.junit.Before;
import org.junit.Test;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link AbstractSingleTracker} class.
 */
public class AbstractSingleTrackerTest extends TestSupport {

    private SingleTracker<Object> tracker;

    @Before
    public void setUp() {
        tracker = new SimpleSingleTracker();
    }

    @Test
    public void testBindAndUnbindObject() {
        Object o = new Object();

        // bind object to tracker
        tracker.bind(o);

        // verify if object is binded
        assertEquals(o, tracker.service());

        // unbind object from tracker
        tracker.unbind(o);

        // verift if object is unbinded
        assertNull(tracker.service());
    }

    class SimpleSingleTracker extends AbstractSingleTracker<Object> {
    }
}
