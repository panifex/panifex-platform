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
package org.panifex.web.impl.menu;

import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link MenuItemComparator} class.
 */
public final class MenuItemComparatorTest extends TestSupport {

    private MenuItemComparator comparator = new MenuItemComparator();
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        resetAll();
    }
    
    /**
     * This test compares the two {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * in case the first item has a greater priority than the second's one.
     * <p>
     * The first item must be the greater.
     */
    @Test
    public void compareDifferentPriorityFirstGreaterTest() {
        // variables
        int priority1 = 2;
        int priority2 = 1;
        
        // mock
        MenuItem item1 = createMock(MenuItem.class);
        MenuItem item2 = createMock(MenuItem.class);
        
        // expect getting the items' priorities
        expect(item1.getPriority()).andReturn(priority1);
        expect(item2.getPriority()).andReturn(priority2);
        
        // perform test
        replayAll();
        int result = comparator.compare(item1, item2);
        verifyAll();
        
        // the item1 is greater than the item2
        assertEquals(1, result);
    }
    
    /**
     * This test compares the two {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * in case the second item has a greater priority than the first's one.
     * <p>
     * The second item must be the greater.
     */
    @Test
    public void compareDifferentPrioritySecondGreaterTest() {
        // variables
        int priority1 = 1;
        int priority2 = 2;
        
        // mock
        MenuItem item1 = createMock(MenuItem.class);
        MenuItem item2 = createMock(MenuItem.class);
        
        // expect getting the items' priorities
        expect(item1.getPriority()).andReturn(priority1);
        expect(item2.getPriority()).andReturn(priority2);
        
        // perform test
        replayAll();
        int result = comparator.compare(item1, item2);
        verifyAll();
        
        // the item2 is greater than the item1
        assertEquals(-1, result);
    }
    
    /**
     * This test compares the two {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * in case the items have the same priority, but the first item has a greater label 
     * than the second's one.
     * <p>
     * The first item must be the greater.
     */
    @Test
    public void compareSamePriorityDifferentLabelFirstGreaterTest() {
        // variables
        int priority = 1;
        String label1 = "A";
        String label2 = "B";
        
        // mock
        MenuItem item1 = createMock(MenuItem.class);
        MenuItem item2 = createMock(MenuItem.class);
        
        // expect getting the items' priorities
        expect(item1.getPriority()).andReturn(priority);
        expect(item2.getPriority()).andReturn(priority);
        
        // expect getting the items' labels
        expect(item1.getLabel()).andReturn(label1);
        expect(item2.getLabel()).andReturn(label2);
        
        // perform test
        replayAll();
        int result = comparator.compare(item1, item2);
        verifyAll();
        
        // the item2 is greater than the item1
        assertEquals(-1, result);
    }
    
    /**
     * This test compares the two {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * in case the items have the same priority, but the secon item has a greater label 
     * than the first's one.
     * <p>
     * The second item must be the greater.
     */
    @Test
    public void compareSamePriorityDifferentLabelSecondGreaterTest() {
        // variables
        int priority = 1;
        String label1 = "B";
        String label2 = "A";
        
        // mock
        MenuItem item1 = createMock(MenuItem.class);
        MenuItem item2 = createMock(MenuItem.class);
        
        // expect getting the items' priorities
        expect(item1.getPriority()).andReturn(priority);
        expect(item2.getPriority()).andReturn(priority);
        
        // expect getting the items' labels
        expect(item1.getLabel()).andReturn(label1);
        expect(item2.getLabel()).andReturn(label2);
        
        // perform test
        replayAll();
        int result = comparator.compare(item1, item2);
        verifyAll();
        
        // the item1 is greater than the item2
        assertEquals(1, result);
    }
    
    /**
     * This test compares the two {@link org.panifex.module.api.menu.MenuItem MenuItem}s
     * in case the items have the same priority and the same labels.
     * <p>
     * The items must be equal.
     */
    @Test
    public void compareSamePrioritiesAndSameLabelsTest() {
        // variables
        int priority = 1;
        String label = getRandomChars(20);
        
        // mock
        MenuItem item1 = createMock(MenuItem.class);
        MenuItem item2 = createMock(MenuItem.class);
        
        // expect getting the items' priorities
        expect(item1.getPriority()).andReturn(priority);
        expect(item2.getPriority()).andReturn(priority);
        
        // expect getting the items' labels
        expect(item1.getLabel()).andReturn(label);
        expect(item2.getLabel()).andReturn(label);
        
        // perform test
        replayAll();
        int result = comparator.compare(item1, item2);
        verifyAll();
        
        // the items must be equals
        assertEquals(0, result);
    }
}
