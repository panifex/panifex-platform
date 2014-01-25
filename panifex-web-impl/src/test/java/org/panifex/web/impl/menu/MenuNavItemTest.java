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
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for the {@link MenuNavitem} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MenuNavitem.class)
public final class MenuNavItemTest extends TestSupport {

    /**
     * The {@link MenuNavitem} instance for unit testing.
     */
    private MenuNavitem menuNavitem;
    
    @Before
    public void setUp() {
        // suppress MenuNavItem constructor
        suppress(constructor(MenuNavitem.class));
        
        // create an instance for unit tests
        menuNavitem = new MenuNavitem();
    }
    
    /**
     * Checks getting and setting the bookmark field.
     * <p>
     * The bookmark field must be null after {@link MenuNavItem} 
     * has been initialized.
     */
    @Test
    public void setAndGetOldPasswordFieldTest() {
        // the bookmark field must be null after MenuNavItem has been initialized
        assertNull(menuNavitem.getBookmark());
        
        // set the new bookmark
        String newBookmark = getRandomChars(20);
        menuNavitem.setBookmark(newBookmark);
        
        // check the new bookamrk
        assertEquals(newBookmark, menuNavitem.getBookmark());
    }
}
