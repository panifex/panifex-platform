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
package org.panifex.web.zk.security.login;

import org.panifex.web.spi.security.LoginViewModel;
import org.panifex.web.spi.security.LoginViewModelImpl;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;

public class ZkLoginViewModelImpl extends LoginViewModelImpl {

    @Command(LoginViewModel.SIGN_IN_COMMAND_)
    @Override
    public void signIn() {
        super.signIn();
    }

    @Command(RESET_COMMAND_)
    @Override
    public void reset() {
        super.reset();

        // notify view
        BindUtils.postNotifyChange(null, null, this, USERNAME_ATTR);
        BindUtils.postNotifyChange(null, null, this, PASSWORD_ATTR);
    }
}
