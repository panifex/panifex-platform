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
package org.panifex.module.api.event;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.event.RedirectToURIEventListener;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;

/**
 * Unit tests for the {@link RedirectToURIEventListener} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    Executions.class,
    Event.class,
    Sessions.class})
public final class RedirectToURIEventListenerTest extends TestSupport {
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        // mock static classes
        mockStatic(Executions.class);
        mockStatic(Sessions.class);
    }
    
    /**
     * This test case creates the event listener and sends the event which
     * occurs sending the redirection to the specified uri.
     */
    @Test
    public void successfullyRedirectTest() throws Exception {
        // variables
        String eventName = getRandomChars(20);
        String uri = getRandomChars(20);

        // mocks
        Event event = createMock(Event.class);
        
        // expect getting the event name
        expect(event.getName()).andReturn(eventName);
            
        // expect redirecting to the uri
        Executions.sendRedirect(uri);
        
        replayAll();
        
        // create the event listener
        RedirectToURIEventListener eventListener = 
                new RedirectToURIEventListener(eventName, uri);
        
        // send event
        eventListener.onEvent(event);
        
        verifyAll();
    }
    
    /**
     * This test case creates the event listener and sends the event which
     * occurs sending the redirection to the specified uri.
     * <p>
     * During the redirection, a defined parameters would be passed over the
     * current {@link org.zkoss.zk.ui.Session} instance.
     */
    @Test
    public void successfullyRedirectPassedParamsTest() throws Exception {
        // variables
        String eventName = getRandomChars(20);
        String uri = getRandomChars(20);
        String paramsAttributeName = getRandomChars(20);
        Map<String, Object> params = new HashMap<>();

        // mocks
        Event eventMock = createMock(Event.class);
        Session sessionMock = createMock(Session.class);
        
        // expect getting the event name
        expect(eventMock.getName()).andReturn(eventName);

        // expect setting parameters to the current session instance
        expect(Sessions.getCurrent()).andReturn(sessionMock);
        expect(sessionMock.setAttribute(paramsAttributeName, params)).andReturn(null);
        
        // expect redirecting to the uri
        Executions.sendRedirect(uri);
        
        replayAll();
        
        // create the event listener
        RedirectToURIEventListener eventListener = 
                new RedirectToURIEventListener(eventName, uri, paramsAttributeName, params);
        
        // send event
        eventListener.onEvent(eventMock);
        
        verifyAll();
    }
    
    /**
     * This test creates the event listener and sends the event which does
     * not occur the redirection to the specified uri.
     */
    @Test
    public void sendNotMappedEventTest() throws Exception {
        // variables
        String mappedEventName = "mappedEvent";
        String otherEventName = "otherEvent";
        String uri = getRandomChars(20);

        // mocks
        Event event = createMock(Event.class);
        
        // expect getting the event name
        expect(event.getName()).andReturn(otherEventName);
            
        replayAll();
        
        // create the event listener
        RedirectToURIEventListener eventListener = 
                new RedirectToURIEventListener(mappedEventName, uri);
        
        // send event
        eventListener.onEvent(event);
        
        verifyAll();
    }
    
    /**
     * This test tries to construct the {@link RedirectToURIEventListener} with
     * the unknown event's name.
     * <p>
     * The {@link RedirectToURIEventListener}'s constructor must throw a 
     * {@link IllegalArgumentException} exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void createEventListenerWithNullEventNameTest() {
        // variables
        String uri = getRandomChars(20);
        
        // create the event listener with the unknown event's name
        new RedirectToURIEventListener(null, uri);
        
        // IllegalArgumentException must be thrown, so fail
        fail("IllegalArgumentException must be thrown");
    }
    
    /**
     * This test tries to construct the {@link RedirectToURIEventListener} with
     * the unknown uri.
     * <p>
     * The unknown uri is legal, so the {@link RedirectToURIEventListener} must be
     * successfully constructed.
     */
    @Test
    public void createEventListenerWithNullURITest() {
        // variables
        String eventName = getRandomChars(20);
        
        // create the event listener with the unknown uri
        RedirectToURIEventListener eventListener = 
                new RedirectToURIEventListener(eventName, null);
        
        // the event listener must be successfully created
        assertNotNull(eventListener);
    }
}
