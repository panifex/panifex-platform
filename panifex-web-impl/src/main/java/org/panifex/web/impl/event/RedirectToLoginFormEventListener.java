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

import org.panifex.module.api.event.RedirectToURIEventListener;
import org.panifex.web.impl.view.login.LoginFormRichlet;
import org.zkoss.zul.Messagebox;

/**
 * {@link org.panifex.module.api.event.RedirectToURIEventListener RedirectToURIEventListener} that 
 * redirects to the login form if the {@link org.zkoss.zk.ui.event.Event Event} with the 
 * specified name occurs.
 * 
 */
public final class RedirectToLoginFormEventListener extends RedirectToURIEventListener {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 7626188306990959044L;

    /**
     * Constructs the {@link RedirectToLoginFormEventListener} which redirects to the login
     * form it the {@link org.zkoss.zul.Messagebox#ON_OK ON_OK} event happened.
     */
    public RedirectToLoginFormEventListener() {
        this(Messagebox.ON_OK);
    }

    /**
     * Constructs the {@link RedirectToLoginFormEventListener} which redirects to the login
     * form if the event with the specified name is occurred.
     */
    public RedirectToLoginFormEventListener(String eventName) {
        super(eventName, LoginFormRichlet.URL);
    }
}
