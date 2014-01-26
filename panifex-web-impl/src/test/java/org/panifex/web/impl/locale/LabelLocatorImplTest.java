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
package org.panifex.web.impl.locale;

import java.net.URL;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for the {@link LabelLocatorImpl} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LabelLocatorImpl.class)
public final class LabelLocatorImplTest extends TestSupport {

    private LabelLocatorImpl labelLocator;
    
    // mocks
    private Locale locatorLocaleMock = createMock(Locale.class);
    private URL locatorUrlMock = createMock(URL.class); 
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        resetAll();
        
        // construct LabelLocatorImpl
        labelLocator = new LabelLocatorImpl(locatorLocaleMock, locatorUrlMock);
    }
    
    /**
     * This test tries to get a resource for the matched locale.
     * <p>
     * The resource must be returned and the same as the mocked one.
     */
    @Test
    public void matchedLocaleTest() {
        // perform test
        replayAll();
        URL url = labelLocator.locate(locatorLocaleMock);
        verifyAll();
        
        // the resources must be the same
        assertEquals(locatorUrlMock, url);
    }
    
    /**
     * This test tries to get a resource for the locale which
     * does not match the locator's.
     * <p>
     * The locator must not return any resource.
     */
    @Test
    public void notMatchedLocaleTest() {
        // mocks
        Locale localeMock = createMock(Locale.class);
        
        // perform test
        replayAll();
        URL url = labelLocator.locate(localeMock);
        verifyAll();
        
        // the resource must not be returned
        assertNull(url);
    }
    

}
