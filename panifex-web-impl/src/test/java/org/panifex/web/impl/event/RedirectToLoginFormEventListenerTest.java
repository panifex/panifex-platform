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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.view.login.LoginFormRichlet;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

/**
 * Unit tests for the {@link RedirectToLoginFormEventListener} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    Event.class,
    Executions.class})
public final class RedirectToLoginFormEventListenerTest extends TestSupport {
    
    /**
     * This test creates the {@link RedirectToLoginFormEventListener} and sends the 
     * {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event which occurs redirection to the login form.
     */
    @Test
    public void redirectOnOkEventTest() throws Exception {
        // mocks
        mockStatic(Executions.class);
        Event event = createMock(Event.class);
        
        // expect getting the event name
        expect(event.getName()).andReturn(Messagebox.ON_OK);
            
        // expect redirecting to the uri
        Executions.sendRedirect(LoginFormRichlet.URL);
        
        replayAll();
        
        // create the event listener
        RedirectToLoginFormEventListener eventListener = 
                new RedirectToLoginFormEventListener();
        
        // send event
        eventListener.onEvent(event);
        
        verifyAll();
    }

    
    /**
     * This test creates the {@link RedirectToLoginFormEventListener} with the specified
     * event name and sends the event which occurs redirection to the login form.
     */
    @Test
    public void redirectOnSpecifiedEventNameTest() throws Exception {
        // variables
        String eventName = getRandomChars(20);

        // mocks
        mockStatic(Executions.class);
        Event event = createMock(Event.class);
        
        // expect getting the event name
        expect(event.getName()).andReturn(eventName);
            
        // expect redirecting to the uri
        Executions.sendRedirect(LoginFormRichlet.URL);
        
        replayAll();
        
        // create the event listener
        RedirectToLoginFormEventListener eventListener = 
                new RedirectToLoginFormEventListener(eventName);
        
        // send event
        eventListener.onEvent(event);
        
        verifyAll();
    }
    
    /**
     * This test creates the {@link RedirectToLoginFormEventListener} and sends the event which does
     * not occur the redirection to the specified uri.
     */
    @Test
    public void sendNotMappedEventTest() throws Exception {
        // variables
        String eventName = "mappedEvent";
        String otherEventName = "otherEvent";

        // mocks
        mockStatic(Executions.class);
        Event event = createMock(Event.class);
        
        // expect getting the event name
        expect(event.getName()).andReturn(otherEventName);
            
        replayAll();
        
        // create the event listener
        RedirectToLoginFormEventListener eventListener = 
                new RedirectToLoginFormEventListener(eventName);
        
        // send event
        eventListener.onEvent(event);
        
        verifyAll();
    }
    
    /**
     * This test tries to construct the {@link RedirectToLoginFormEventListener} with
     * the unknown event's name.
     * <p>
     * The {@link RedirectToLoginFormEventListener}'s constructor must throw a 
     * {@link IllegalArgumentException} exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void createEventListenerWithNullEventNameTest() {
        // create the event listener with the unknown event's name
        new RedirectToLoginFormEventListener(null);
        
        // IllegalArgumentException must be thrown, so fail
        fail("IllegalArgumentException must be thrown");
    }
}
