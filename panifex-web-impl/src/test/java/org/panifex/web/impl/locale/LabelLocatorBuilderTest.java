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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.locale.LocaleService;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.util.resource.LabelLocator;

/**
 * Unit tests for the {@link LabelLocatorBuilder} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    LabelLocatorBuilder.class,
    LabelLocatorImpl.class})
public final class LabelLocatorBuilderTest extends TestSupport {

    /**
     * Prepares an environment for executing unit tests.
     */
    @Before
    public void setUp() {
        resetAll();
    }
    
    /**
     * This test tries to successfully build {@link org.zkoss.util.resource.LabelLocator LabelLocator}
     * based on the valid {@link org.panifex.module.api.locale.LocaleService LocaleService} instance.
     * <p>
     * The LabelLocator must be built.
     */
    @Test
    public void successfullyBuildLabelLocatorTest() throws Exception {
        // variables
        Set<URL> urls = new HashSet<>();
        
        // mocks
        LocaleService localeServiceMock = createMock(LocaleService.class);
        Locale localeMock = createMock(Locale.class);
        URL urlMock = createMock(URL.class);
        
        // adds urls to the set
        urls.add(urlMock);
        
        // expect getting supported locale
        expect(localeServiceMock.supportedLocale()).andReturn(localeMock);
        
        // expect getting resources' locates
        expect(localeServiceMock.locates()).andReturn(urls);
        
        // expect creating a new LabelLocator
        LabelLocatorImpl labelLocatorMock = createMockAndExpectNew(
            LabelLocatorImpl.class, localeMock, urlMock);
        
        // perform test
        replayAll();
        List<LabelLocator> labelLocators = new LabelLocatorBuilder(localeServiceMock).build();
        verifyAll();
        
        // verify if the locator is successfully built
        assertNotNull(labelLocators);
        
        // it must contains a label locator
        assertEquals(1, labelLocators.size());
        assertEquals(labelLocatorMock, labelLocators.get(0));
    }
    
    /**
     * This test tries to build {@link org.zkoss.util.resource.LabelLocator LabelLocator}
     * based on the unknown LocaleService.
     * <p>
     * NullPointerException must be thrown.
     */
    @Test(expected = NullPointerException.class)
    public void localeServiceIsNullTest() {
        new LabelLocatorBuilder(null);
    }
    
    /**
     * This test tries to build {@link org.zkoss.util.resource.LabelLocator LabelLocator}
     * in case the {@link LocaleService#supportedLocale()} is not defined (null).
     * <p>
     * IllegalArgumentException must be thrown.
     */
    @Test
    public void localeUnknownTest() {
        // mocks
        LocaleService localeServiceMock = createMock(LocaleService.class);
        
        // expect getting the undefined locale
        expect(localeServiceMock.supportedLocale()).andReturn(null);
        
        // perform test
        replayAll();
        try {
            new LabelLocatorBuilder(localeServiceMock);
            fail("IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException is expected
        }
        verifyAll();
    }
    
    /**
     * This test tries to build {@link org.zkoss.util.resource.LabelLocator LabelLocator}
     * in case the {@link LocaleService#locates()} is not defined (null).
     * <p>
     * IllegalArgumentException must be thrown.
     */
    @Test
    public void locatesUnknownTest() {
        // mocks
        LocaleService localeServiceMock = createMock(LocaleService.class);
        Locale localeMock = createMock(Locale.class);
        
        // expect getting the locale
        expect(localeServiceMock.supportedLocale()).andReturn(localeMock);
        
        // expect getting the unknown locates
        expect(localeServiceMock.locates()).andReturn(null);
        
        // perform test
        replayAll();
        try {
            new LabelLocatorBuilder(localeServiceMock);
            fail("IllegalArgumentException must be thrown");
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException is expected
        }
        verifyAll();
    }
}
