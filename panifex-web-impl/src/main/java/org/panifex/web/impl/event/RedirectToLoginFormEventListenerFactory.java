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

import org.panifex.module.api.event.RedirectToURIEventListener;
import org.panifex.web.impl.view.login.LoginFormRichlet;
import org.panifex.web.impl.view.login.LoginFormVM;
import org.zkoss.zul.Messagebox;

/**
 * The factory of the {@link RedirectToURIEventListener} classes which
 * redirects the user to the {@link org.panifex.web.impl.view.login.LoginFormRichlet LoginForm}.
 */
public final class RedirectToLoginFormEventListenerFactory {

    /**
     * Private construct which protects the factory from instancing - singleton.
     */
    private RedirectToLoginFormEventListenerFactory() {
    }
    
    /**
     * Creates the {@link RedirectToURIEventListener} which redirects the user to the 
     * login form it the {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event happened.
     *  
     * @return the {@link RedirectToURIEventListener} which redirects the user to the login form
     */
    public static RedirectToURIEventListener createDefaultRedirector() {
        return createDefaultRedirector(null);
    }
    
    /**
     * Creates the {@link RedirectToURIEventListener} which redirects the user to the 
     * login form it the {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event happened.
     * <p>
     * The username parameter is going to be passed to the login form through 
     * {@link org.zkoss.zk.ui.Session Session}'s parameters, if it is not null.
     * The passed username will be put in the username text box on the login form.
     * 
     * @param username the username which is going to be passed to the login form
     * 
     * @return the {@link RedirectToURIEventListener} which redirects the user to the login form
     */
    public static RedirectToURIEventListener createDefaultRedirector(String username) {
        return createRedirectorForEvent(Messagebox.ON_OK, username);
    }
    
    /**
     * Creates the {@link RedirectToURIEventListener} which redirects the user to the 
     * login form it the specified event happened.
     * <p>
     * If the specified event name is null, then it will redirect the user on the 
     * {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event.
     * 
     * @param eventName the event name when the redirect to the login form is sent
     * 
     * @return the {@link RedirectToURIEventListener} which redirects the user to the login form
     */
    public static RedirectToURIEventListener createRedirectorForEvent(String eventName) {
        return createRedirectorForEvent(eventName, null);
    }
    
    /**
     * Creates the {@link RedirectToURIEventListener} which redirects the user to the 
     * login form it the specified event happened.
     * <p>
     * The username parameter is going to be passed to the login form through 
     * {@link org.zkoss.zk.ui.Session Session}'s parameters, if it is not null.
     * The passed username will be put in the username text box on the login form.
     * <p>
     * If the specified event name is null, then it will redirect the user on the 
     * {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event.
     * 
     * @param eventName the event name when the redirect to the login form is sent
     * @param username the username which is going to be passed to the login form
     * 
     * @return the {@link RedirectToURIEventListener} which redirects the user to the login form
     */
    public static RedirectToURIEventListener createRedirectorForEvent(String eventName, String username) {
        // the url of login form
        String url = LoginFormRichlet.URL;

        // if the event is null, then redirect on the Messagebox#ON_OK event
        if (eventName == null) {
            eventName = Messagebox.ON_OK;
        }
        
        // check if the username is specified
        if (username != null) {
            // the username is specified, so send it through session's parameters
            Map<String, Object> params = new HashMap<>();
            params.put(LoginFormVM.USERNAME_PARAM, username);
            RedirectToURIEventListener eventListener = new RedirectToURIEventListener(eventName, url, LoginFormVM.ID, params);
            return eventListener;
        } else {
            // the username is not specifed, so do not sent it through session's parameters
            RedirectToURIEventListener eventListener = new RedirectToURIEventListener(eventName, url);
            return eventListener;
        }

    }
}
