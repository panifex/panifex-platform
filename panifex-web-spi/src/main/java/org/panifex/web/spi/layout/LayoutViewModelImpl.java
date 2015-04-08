/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.web.spi.layout;

import org.apache.commons.lang3.Validate;
import org.apache.shiro.subject.Subject;
import org.panifex.module.api.security.SecurityUtilService;
import org.panifex.module.api.security.SecurityUtilServiceTracker;

/**
 * A base layout view-model implementation that you can extend to write your own view-models.
 * <p>
 * Instead of implementing {@link LayoutViewModel} directly it it often simpler to just extend this class.
 */
public class LayoutViewModelImpl implements LayoutViewModel {

    // trackers
    protected final SecurityUtilServiceTracker securityUtilServiceTracker;

    /**
     * Creates a default instance.
     *
     * @param securityUtilServiceTracker the security util service tracker
     */
    public LayoutViewModelImpl(
            SecurityUtilServiceTracker securityUtilServiceTracker) {
        this.securityUtilServiceTracker = Validate.notNull(securityUtilServiceTracker);
    }

    @Override
    public boolean getIsUserAuthenticated() {
        Subject currentSubject = getCurrentSubject();

        // subject should be authenticated
        return currentSubject.isAuthenticated();
    }

    @Override
    public boolean getIsUserLogged() {
        Subject currentSubject = getCurrentSubject();

        // subject should be remembered or authenticated
        return currentSubject.isRemembered() || currentSubject.isAuthenticated();
    }

    protected Subject getCurrentSubject() {
        SecurityUtilService service = securityUtilServiceTracker.service();
        return service.getSubject();
    }
}
