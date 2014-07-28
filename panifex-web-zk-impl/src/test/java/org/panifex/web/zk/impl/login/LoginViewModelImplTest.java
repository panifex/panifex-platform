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
package org.panifex.web.zk.impl.login;

import org.junit.Test;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link LoginViewModelImpl} class.
 */
public final class LoginViewModelImplTest extends TestSupport {

    private LoginViewModelImpl viewModel = new LoginViewModelImpl();

    @Test
    public void setAndGetUsernameTest() {
        String username = getRandomChars(20);
        viewModel.setUsername(username);
        assertEquals(username, viewModel.getUsername());
    }

    @Test
    public void setAndGetPasswordTest() {
        String password = getRandomChars(20);
        viewModel.setPassword(password);
        assertEquals(password, viewModel.getPassword());
    }

    @Test
    public void setAndGetIsRememberMeTrueTest() {
        setAndGetIsRememberMeTest(true);
    }

    @Test
    public void setAndGetIsRememberMeFalseTest() {
        setAndGetIsRememberMeTest(false);
    }

    private void setAndGetIsRememberMeTest(boolean isRememberMe) {
        viewModel.setIsRememberMe(isRememberMe);
        assertEquals(isRememberMe, viewModel.getIsRememberMe());
    }
}
