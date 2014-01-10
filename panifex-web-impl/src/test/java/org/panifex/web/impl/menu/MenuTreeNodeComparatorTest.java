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
import org.junit.runner.RunWith;
import org.panifex.module.api.menu.MenuItem;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zul.DefaultTreeNode;

/**
 * Unit tests for the {@link MenuTreeNodeComparator} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    MenuItemComparator.class,
    MenuTreeNodeComparator.class})
public final class MenuTreeNodeComparatorTest extends TestSupport {

    /**
     * The {@link MenuTreeNodeComparator} instance to be unit tested.
     */
    private MenuTreeNodeComparator comparator;
    
    /**
     * Mocked {@link MenuItemComparator} which compares two 
     * {@link org.panifex.module.api.menu.MenuItem MenuItem}s.
     */
    private MenuItemComparator menuItemComparatorMock;
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() throws Exception {
        resetAll();
        
        // mocks
        menuItemComparatorMock = createMockAndExpectNew(MenuItemComparator.class);
        
        // create the comparator
        replayAll();
        comparator = new MenuTreeNodeComparator();
        verifyAll();
        resetAll();
    }
    
    /**
     * This test successfully compares the two {@link org.zkoss.zul.DefaultTreeNode DefaultTreeNode}s.
     * <p>
     * The tree nodes must be successfully compared.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void successfullyCompareTest() {
        // variables
        int expectedResult = 0;
        
        // mocks
        DefaultTreeNode<MenuItem> treeNode1 = createMock(DefaultTreeNode.class);
        DefaultTreeNode<MenuItem> treeNode2 = createMock(DefaultTreeNode.class);
        MenuItem menuItem1 = createMock(MenuItem.class);
        MenuItem menuItem2 = createMock(MenuItem.class);
        
        // expect getting the tree nodes' data
        expect(treeNode1.getData()).andReturn(menuItem1);
        expect(treeNode2.getData()).andReturn(menuItem2);
        
        // expect comparing the menu items
        expect(menuItemComparatorMock.compare(menuItem1, menuItem2)).andReturn(expectedResult);
        
        // perform test
        replayAll();
        int comparisonResult = comparator.compare(treeNode1, treeNode2);
        verifyAll();
        
        // the results of the comparison must be the same
        assertEquals(expectedResult, comparisonResult);
    }
}
