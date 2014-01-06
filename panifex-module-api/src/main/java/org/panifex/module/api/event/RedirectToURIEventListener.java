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

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;

/**
 * {@link org.zkoss.zk.ui.event.SerializableEventListener SerializableEventListener} that 
 * redirects to the specified <a href="http://en.wikipedia.org/wiki/Uniform_resource_identifier">URI</a> 
 * if the {@link org.zkoss.zk.ui.event.Event Event} with the specified name occurs.
 * 
 */
public class RedirectToURIEventListener implements SerializableEventListener<Event> {
    
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -8649297329016145043L;
    
    /**
     * The event's name when the redirect is sent.
     */
    private final String eventName;
    
    /**
     * The URI to redirect to, or null to reload the same page.
     */
    private final String uri;
    
    /**
     * The attribute name on which the parameters would be passed over the current
     * {@link org.zkoss.zk.ui.Session} instance.
     */
    private final String paramsAttributeName;
    
    /**
     * The parameters which can be passed over the current {@link org.zkoss.zk.ui.Session}
     * instance.
     */
    private final Map<String, Object> params;
    
    /**
     * Constructs a new {@link RedirectToURIEventListener} instance.
     * 
     * @param eventName the event name when the redirect to the login form is sent
     * @param uri the URI to redirect to, or null to reload the same page
     */
    public RedirectToURIEventListener(String eventName, String uri) {
       this(eventName, uri, null, null);
    }
    
    /**
     * Constructs a new {@link RedirectToURIEventListener} instance.
     * 
     * @param eventName the event name when the redirect to the login form is sent
     * @param uri the URI to redirect to, or null to reload the same page
     * @param paramsAttributeName the attribute name on which the parameters would be passed
     * @param params The parameters which can be passed over the current {@link org.zkoss.zk.ui.Session}
     */
    public RedirectToURIEventListener(String eventName, String uri, String paramsAttributeName, Map<String, Object> params) {
        // check the event name
        if (eventName != null) {
            this.eventName = eventName;
        } else {
            // the event name is null. Throw IllegalArgumentException
            throw new IllegalArgumentException("The event name must not be null. It must be defined.");
        }
        
        // map the uri
        this.uri = uri;
        
        // map the session's params
        this.paramsAttributeName = paramsAttributeName;
        this.params = params;
    }

    /**
     * Sends the redirect to the uri when the {@link org.zkoss.zk.ui.event.Event Event}
     * with the specified name occurs.
     */
    @Override
    public final void onEvent(Event event) throws Exception {
        if (eventName.equals(event.getName())) {
            // set session parameters
            setSessionParameters();
            
            // send redirect to the login form
            Executions.sendRedirect(uri);
        }
    }
    
    /**
     * Sets the parameters which would be passed over the current {@link org.zkoss.zk.ui.Session}
     * instance.
     */
    protected void setSessionParameters() {
        if (paramsAttributeName != null && params != null) {
            Sessions.getCurrent().setAttribute(paramsAttributeName, params);
        }
    }

}
