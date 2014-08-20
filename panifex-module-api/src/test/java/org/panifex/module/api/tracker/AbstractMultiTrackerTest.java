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
 * Unit tests for {@link AbstractMultiTracker} class.
 */
public class AbstractMultiTrackerTest extends TestSupport {

    private MultiTracker<Object> tracker;

    @Before
    public void setUp() {
        tracker = new SimpleMultiTracker();
    }

    @Test
    public void testBindAndUnbindObjects() {
        Object o1 = new Object();
        Object o2 = new Object();

        // bind objects to tracker
        tracker.bind(o1);
        tracker.bind(o2);

        // verify if objects are binded
        assertEquals(o1, tracker.services().get(0));
        assertEquals(o2, tracker.services().get(1));
        assertNotEquals(o1, tracker.services().get(1));

        // unbind first object from tracker
        tracker.unbind(o1);

        // verift if object is unbinded
        assertEquals(o2, tracker.services().get(0));
    }

    class SimpleMultiTracker extends AbstractMultiTracker<Object> {
    }
}
