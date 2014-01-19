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
package org.panifex.web.impl.settings;

import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.settings.SettingsContent;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for the {@link SettingsContentManager} class.
 */
public final class SettingsContentManagerTest extends TestSupport {

    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        SettingsContentManager.init();
    }
    
    /**
     * This test tries to initialize the manager by calling the {@link SettingsContentManager#init()}
     * method.
     * <p>
     * The manager must be successfully initialized (not null).
     */
    @Test
    public void initTest() {
        // initialize a manager
        SettingsContentManager manager = SettingsContentManager.init();
        
        // manager must not be null
        assertNotNull(manager);
    }
    
    /**
     * This test tries to bind the {@link org.panifex.module.api.settings.SettingsContent SettingsContent}
     * to the manager.
     * <p>
     * The content must be successfully binded.
     */
    @Test
    public void bindSettingsContentTest() {
        // mocks
        SettingsContent contentMock = createMock(SettingsContent.class);
        
        // bind the content
        SettingsContentManager.init().bind(contentMock);
        
        // verify if the content is binded
        int position = SettingsContentManager.getContents().indexOf(contentMock);
        
        // position is positive if the content is successfully binded
        assertTrue(position >= 0);
    }
    
    /**
     * This test tries to bind the {@link org.panifex.module.api.settings.SettingsContent SettingsContent}
     * to the manager and then to unbind it.
     * <p>
     * The content must be successfully binded and unbinded.
     */
    @Test
    public void bindAndUnbindSettingsContentTest() {
        // mocks
        SettingsContent contentMock = createMock(SettingsContent.class);
        
        // bind the content
        SettingsContentManager.init().bind(contentMock);
        
        // verify if the content is binded
        int position = SettingsContentManager.getContents().indexOf(contentMock);
        
        // position is positive if the content is successfully binded
        assertTrue(position >= 0);
        
        // unbind the content
        SettingsContentManager.init().unbind(contentMock);
        
        // verify if the content is unbinded, index must be -1 if the content is unbinded
        assertEquals(-1, SettingsContentManager.getContents().indexOf(contentMock));
    }
}
