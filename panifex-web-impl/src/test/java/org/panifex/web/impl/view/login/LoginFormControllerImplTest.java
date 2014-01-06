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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.view.main.MainRichlet;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.zk.ui.Executions;

/**
 * Unit tests for the {@link LoginFormControllerImpl} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    Executions.class})
public final class LoginFormControllerImplTest extends TestSupport {

    /**
     * The {@link LoginFormControllerImpl} instance for unit testing.
     */
    private LoginFormControllerImpl controller = new LoginFormControllerImpl();
    
    /**
     * Prepares an environment for performing unit tests.
     */
    @Before
    public void setUp() {
        mockStatic(Executions.class);
    }
    
    /**
     * This test checks the {@link LoginFormControllerImpl#onSuccessfulLoginIn()}
     * method. It redirects the logged user to the main form.
     */
    @Test
    public void onSuccessfulLoginInTest() {
        // expect sending the redirection to the main form
        Executions.sendRedirect(MainRichlet.URL);
        
        // perform test
        replayAll();
        controller.onSuccessfulLoginIn();
        verifyAll();
    }
    
    @Test
    public void onExpiredCredentialsExceptionTest() {
        // TODO
    }
}
