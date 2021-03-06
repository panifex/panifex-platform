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
package org.panifex.web.spi.security;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.security.SecurityUtilServiceTracker;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link LoginViewModelImpl} class.
 */
public final class LoginViewModelImplTest extends TestSupport {

    // trackers
    private final SecurityUtilServiceTracker securityUtilServiceTrackerMock =
            createMock(SecurityUtilServiceTracker.class);

    private final LoginViewModelImpl viewModel =
            new LoginViewModelImpl(securityUtilServiceTrackerMock);

    @Before
    public void setUp() {
        resetAll();
    }

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

    @Test
    public void testResetButton() {
        // set initial username and password
        String initialUsername = getRandomChars(20);
        String initialPassword = getRandomChars(20);
        viewModel.setUsername(initialUsername);
        viewModel.setPassword(initialPassword);

        // assert initial username and password are not null
        assertEquals(initialUsername, viewModel.getUsername());
        assertEquals(initialPassword, viewModel.getPassword());

        // perform test
        replayAll();
        viewModel.reset();
        verifyAll();

        // assert username and password are null
        assertEquals(StringUtils.EMPTY, viewModel.getUsername());
        assertEquals(StringUtils.EMPTY, viewModel.getPassword());
    }
}
