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
package org.panifex.web.impl.view.login;

import java.util.HashMap;

import org.panifex.web.impl.i18n.SecurityLabels;
import org.panifex.web.impl.view.main.MainRichlet;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zul.Messagebox;

/**
 * Implements actions of the {@link LoginFormController} controller.
 *
 */
public final class LoginFormControllerImpl implements LoginFormController {

    /**
     * Sends the redirection to the main form.
     */
    @Override
    public void onSuccessfulLoginIn() {
        Executions.sendRedirect(MainRichlet.URL);
    }
    
    /**
     * Shows the message box to the user and then redirects it to the
     * change password form.
     */
    @Override
    public void onExpiredCredentialsException(final String username) {
     // show warning message box
        Messagebox.show(
            Labels.getLabel(SecurityLabels.ON_EXPIRED_CREDENTIALS_EXCEPTION_MESSAGE), 
            Labels.getLabel(SecurityLabels.ON_EXPIRED_CREDENTIALS_EXCEPTION_TITLE), 
            Messagebox.OK, 
            Messagebox.EXCLAMATION,
            new SerializableEventListener<Event>() {

                /**
                 * Serial version UID
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public void onEvent(Event event) throws Exception {
                    if (Messagebox.ON_OK.equals(event.getName())) {
                        final HashMap<String, Object> map = new HashMap<>();
                        map.put(ChangePasswordFormVM.USERNAME_PARAM, username);
                        Sessions.getCurrent().setAttribute(ChangePasswordFormVM.ID, map);
                        Executions.sendRedirect("/zk/changepassword");
                    }       
                }
                
            });
    }

}
