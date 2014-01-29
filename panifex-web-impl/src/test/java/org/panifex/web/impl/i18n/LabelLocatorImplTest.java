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
package org.panifex.web.impl.i18n;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.i18n.LabelLocatorImpl;
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
     * This test tries to match the unknown locale. It is used
     * when the locale is not specified.
     */
    @Test
    public void matchUnknownLocaleTest() {
        // expect getting the not specified language
        expect(locatorLocaleMock.getLanguage()).andReturn(StringUtils.EMPTY);
        
        // perform test
        replayAll();
        URL url = labelLocator.locate(null);
        verifyAll();
        
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
    
    /**
     * This test checks the equals contract.
     * 
     * @see {@link java.lang.Object#equals(Object)}
     * @see {@link java.lang.Object#hashCode()}
     */
    @Test
    public void equalsContractTest() throws MalformedURLException {
        // mocks
        Locale locale1Mock = new Locale("en");
        Locale locale2Mock = new Locale("de");
        URL url1Mock = new URL("http://url1");
        URL url2Mock = new URL("http://url2");
        
        // create locators
        List<LabelLocatorImpl> locators = new ArrayList<LabelLocatorImpl>();
        
        LabelLocatorImpl locator1 = new LabelLocatorImpl(locale1Mock, url1Mock);
        locators.add(locator1);
        locators.add(new LabelLocatorImpl(locale1Mock, url2Mock));
        locators.add(new LabelLocatorImpl(locale2Mock, url1Mock));
        locators.add(new LabelLocatorImpl(locale2Mock, url2Mock));
        
        LabelLocatorImpl locator2 = new LabelLocatorImpl(locale1Mock, url1Mock);
        
        // verify equals contract
        assertEquals(locator1, locator2);
        
        // verify not equals contract
        for (int first = 0 ; first < locators.size() ; first++) {
            for (int second = first + 1 ; second < locators.size() ; second++) {
                // first and second must not be equal
                assertNotEquals(locators.get(first), locators.get(second));
                assertNotEquals(locators.get(second), locators.get(first));
            }
        }
        
    }
}
