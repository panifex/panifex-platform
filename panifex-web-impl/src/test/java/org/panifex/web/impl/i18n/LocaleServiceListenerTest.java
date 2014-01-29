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

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.i18n.LocaleService;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.i18n.LabelLocatorBuilder;
import org.panifex.web.impl.i18n.LabelLocatorImpl;
import org.panifex.web.impl.i18n.LocaleServiceListener;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.util.resource.LabelLocator;

/**
 * Unit tests for the {@link LocaleServiceListener} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    LabelLocatorBuilder.class,
    LabelLocatorImpl.class,
    LocaleServiceListener.class})
public final class LocaleServiceListenerTest extends TestSupport {

    private LocaleServiceListener listener;
    
    /**
     * Initializes the {@link LocaleServiceListener} before
     * performing unit tests.
     */
    @Before
    public void setUp() {
        listener = LocaleServiceListener.init();
        resetAll();
    }
    
    /**
     * This test case initializes the listener again. It must
     * return the same instance because it is a singleton.
     */
    @Test
    public void initTest() {
        LocaleServiceListener listener = LocaleServiceListener.init();
        
        // the listener must not be null
        assertNotNull(listener);
        
        // it must be equal with the previous instance
        assertEquals(this.listener, listener);
    }
    
    /**
     * Tries to bind LocaleService to the listener.
     */
    @Test
    public void bindLocaleServiceTest() throws Exception {
        // mocks
        LocaleService localeServiceMock = createMock(LocaleService.class);
        LabelLocatorImpl labelLocatorMock = createMock(LabelLocatorImpl.class);
        
        // the collection of the built LabelLocators (see LabelLocatorBuilder#build)
        List<LabelLocator> labelLocators = new ArrayList<>();
        labelLocators.add(labelLocatorMock);
        
        // expect building a new LabelLocatorImpl
        LabelLocatorBuilder labelLocatorBuilderMock = 
                createMockAndExpectNew(LabelLocatorBuilder.class, localeServiceMock);
        expect(labelLocatorBuilderMock.build()).andReturn(labelLocators);
        
        // perform test
        replayAll();
        listener.bind(localeServiceMock);
        verifyAll();
    }
}
