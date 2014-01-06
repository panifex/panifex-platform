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
package org.panifex.web.impl.event;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.event.RedirectToURIEventListener;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.view.login.LoginFormRichlet;
import org.panifex.web.impl.view.login.LoginFormVM;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zul.Messagebox;

/**
 * Unit tests for the {@link RedirectToLoginFormEventListenerFactory} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(RedirectToLoginFormEventListenerFactory.class)
public final class RedirectToLoginFormEventListenerFactoryTest extends TestSupport {

    /**
     * This test tries to create the {@link RedirectToURIEventListener} which redirects the user to the 
     * login form it the {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event happened.
     */
    @Test
    public void createDefaultRedirectorTest() throws Exception {
        // expect creating a new RedirectToURIEventListener
        RedirectToURIEventListener eventListenerMock = 
                createMockAndExpectNew(RedirectToURIEventListener.class, Messagebox.ON_OK, LoginFormRichlet.URL);
        
        // perform test
        replayAll();
        RedirectToURIEventListener createdEventListener =
                RedirectToLoginFormEventListenerFactory.createDefaultRedirector();
        verifyAll();
        
        // event listener must be the same
        assertEquals(eventListenerMock, createdEventListener);
    }
    
    /**
     * This test tries to create the {@link org.panifex.module.api.event.RedirectToURIEventListener RedirectToURIEventListener}
     * which redirects the user to the login form it the {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} 
     * event happened.
     * <p>
     * This test also sends the username parameter to the login form through 
     * {@link org.zkoss.zk.ui.Session Session}'s parameters.
     */ 
    @Test
    public void createDefaultRedirectorWithUsernameTest() throws Exception {
        // variables
        String username = getRandomWord();
        Map<String, Object> params = new HashMap<>();
        params.put(LoginFormVM.USERNAME_PARAM, username);
        
        // expect creating a new RedirectToURIEventListener
        RedirectToURIEventListener eventListenerMock = 
                createMockAndExpectNew(
                    RedirectToURIEventListener.class, 
                    Messagebox.ON_OK, 
                    LoginFormRichlet.URL, 
                    LoginFormVM.ID, params);
        
        // perform test
        replayAll();
        RedirectToURIEventListener createdEventListener =
                RedirectToLoginFormEventListenerFactory.createDefaultRedirector(username);
        verifyAll();
        
        // event listener must be the same
        assertEquals(eventListenerMock, createdEventListener);
    }
    
    /**
     * This test tries to create the {@link org.panifex.module.api.event.RedirectToURIEventListener RedirectToURIEventListener} 
     * which redirects the user to the login form it the {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} 
     * event happened.
     * <p>
     * The username parameter is not going to be passed to the login form because it is not null.
     */ 
    @Test
    public void createDefaultRedirectorWithNullUsernameTest() throws Exception {
        // expect creating a new RedirectToURIEventListener
        RedirectToURIEventListener eventListenerMock = 
                createMockAndExpectNew(RedirectToURIEventListener.class, Messagebox.ON_OK, LoginFormRichlet.URL);
        
        // perform test
        replayAll();
        RedirectToURIEventListener createdEventListener =
                RedirectToLoginFormEventListenerFactory.createDefaultRedirector(null);
        verifyAll();
        
        // event listener must be the same
        assertEquals(eventListenerMock, createdEventListener);
    }
    
    /**
     * This test tries to create the {@link org.panifex.module.api.event.RedirectToURIEventListener RedirectToURIEventListener} 
     * which redirects the user to the login form it the specified event happened.
     */
    @Test
    public void createRedirectorForEventTest() throws Exception {
        // variables
        String eventName = getRandomWord();
        
        // expect creating a new RedirectToURIEventListener
        RedirectToURIEventListener eventListenerMock = 
                createMockAndExpectNew(RedirectToURIEventListener.class, eventName, LoginFormRichlet.URL);
        
        // perform test
        replayAll();
        RedirectToURIEventListener createdEventListener =
                RedirectToLoginFormEventListenerFactory.createRedirectorForEvent(eventName);
        verifyAll();
        
        // event listener must be the same
        assertEquals(eventListenerMock, createdEventListener);
    }
    
    /**
     * This test tries to create the {@link org.panifex.module.api.event.RedirectToURIEventListener RedirectToURIEventListener} 
     * which redirects the user to the login form it the specified event happened.
     * <p>
     * The specified event is null, so the created RedirectToURIEventListener must redirects on the
     * {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event.
     */
    @Test
    public void createRedirectorForNullEventTest() throws Exception {
        
        // expect creating a new RedirectToURIEventListener
        RedirectToURIEventListener eventListenerMock = 
                createMockAndExpectNew(RedirectToURIEventListener.class, Messagebox.ON_OK, LoginFormRichlet.URL);
        
        // perform test
        replayAll();
        RedirectToURIEventListener createdEventListener =
                RedirectToLoginFormEventListenerFactory.createRedirectorForEvent(null);
        verifyAll();
        
        // event listener must be the same
        assertEquals(eventListenerMock, createdEventListener);
    }
    
    /**
     * This test tries to create the {@link org.panifex.module.api.event.RedirectToURIEventListener RedirectToURIEventListener} 
     * which redirects the user to the login form it the specified event happened.
     * <p>
     * This test also sends the username parameter to the login form through 
     * {@link org.zkoss.zk.ui.Session Session}'s parameters.
     */
    @Test
    public void createRedirectorForEventWithUsernameTest() throws Exception {
        // variables
        String eventName = getRandomWord();
        String username = getRandomWord();
        Map<String, Object> params = new HashMap<>();
        params.put(LoginFormVM.USERNAME_PARAM, username);
        
        // expect creating a new RedirectToURIEventListener
        RedirectToURIEventListener eventListenerMock = 
                createMockAndExpectNew(
                    RedirectToURIEventListener.class, 
                    eventName, 
                    LoginFormRichlet.URL,
                    LoginFormVM.ID, 
                    params);
        
        // perform test
        replayAll();
        RedirectToURIEventListener createdEventListener =
                RedirectToLoginFormEventListenerFactory.createRedirectorForEvent(eventName, username);
        verifyAll();
        
        // event listener must be the same
        assertEquals(eventListenerMock, createdEventListener);
    }
}
