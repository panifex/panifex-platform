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
import org.panifex.web.impl.event.RedirectToLoginFormEventListenerFactory;
import org.panifex.web.impl.i18n.SecurityLabels;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

/**
 * Unit tests for the {@link ChangePasswordFormControllerImpl} class.
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    ChangePasswordFormControllerImpl.class,
    Executions.class,
    Labels.class,
    Messagebox.class,
    RedirectToLoginFormEventListenerFactory.class})
public final class ChangePasswordFormControllerImplTest extends TestSupport {

    /**
     * Tested controller.
     */
    private ChangePasswordFormController controller =
            new ChangePasswordFormControllerImpl();

    /**
     * Prepares an environment for executing unit tests.
     */
    @Before
    public void setUp() {
        // mock static classes
        mockStatic(Executions.class);
        mockStatic(Labels.class);
        mockStatic(Messagebox.class);
        mockStatic(RedirectToLoginFormEventListenerFactory.class);
    }

    /**
     * This test checks the {@link ChangePasswordFormControllerImpl#onSuccessfullyChangePassword()}
     * functionality. It shows the message box which informs the user and then it redirects the
     * user to the login form.
     */
    /*
    @Test
    public void onSuccessfullyChangePasswordTest() throws Exception {
        // variables
        String username = getRandomWord();
        String messageLabel = getRandomWord();
        String titleLabel = getRandomWord();

        // mocks
        RedirectToURIEventListener listenerMock =
                createMock(RedirectToURIEventListener.class);

        // expect resolving labels
        expect(Labels.getLabel(SecurityLabels.CHANGEPASSWORD_SUCCESS_MESSAGE)).andReturn(messageLabel);
        expect(Labels.getLabel(SecurityLabels.CHANGEPASSWORD_SUCCESS_TITLE)).andReturn(titleLabel);

        // expect creating redirector to the login form
        expect(RedirectToLoginFormEventListenerFactory.createDefaultRedirector(username)).
            andReturn(listenerMock);

        // expect showing the message box
        expect(Messagebox.show(
            messageLabel,
            titleLabel,
            Messagebox.OK,
            Messagebox.INFORMATION,
            listenerMock)).andReturn(Messagebox.OK);

        replayAll();

        controller.onSuccessfullyChangePassword(username);

        verifyAll();
    }*/

    /**
     * This test checks the {@link ChangePasswordFormControllerImpl#onWrongParametersPassed()}
     * functionality. It shows the message box which informs the user and then it redirects the
     * user to the login form.
     */
    /*
    @Test
    public void onWrongParametersPassedTest() throws Exception {
        // variables
        String messageLabel = getRandomWord();
        String titleLabel = getRandomWord();

        // mocks
        RedirectToURIEventListener listenerMock =
                createMock(RedirectToURIEventListener.class);

        // expect resolving labels
        expect(Labels.getLabel(CommonLabels.ON_EXCEPTION_MESSAGE)).andReturn(messageLabel);
        expect(Labels.getLabel(CommonLabels.ON_EXCEPTION_TITLE)).andReturn(titleLabel);

        // expect creating redirector to the login form
        expect(RedirectToLoginFormEventListenerFactory.createDefaultRedirector()).
            andReturn(listenerMock);

        // expect showing the message box
        expect(Messagebox.show(
            messageLabel,
            titleLabel,
            Messagebox.OK,
            Messagebox.ERROR,
            listenerMock)).andReturn(Messagebox.OK);

        replayAll();

        controller.onWrongParametersPassed();

        verifyAll();
    }*/

    /**
     * This test checks the {@link ChangePasswordFormControllerImpl#onUnknownAccountException()}
     * functionality.
     * <p>
     * It shows the message box which informs the user that the account
     * does not exist and then it redirects the user to the login form.
     */
    /*
    @Test
    public void onUnknownAccountExceptionTest() throws Exception {
        // variables
        String messageLabel = getRandomWord();
        String titleLabel = getRandomWord();

        // mocks
        RedirectToURIEventListener listenerMock =
                createMock(RedirectToURIEventListener.class);

        // expect resolving labels
        expect(Labels.getLabel(SecurityLabels.ON_UNKNOWN_ACCOUNT_EXCEPTION_MESSAGE)).andReturn(messageLabel);
        expect(Labels.getLabel(SecurityLabels.ON_UNKNOWN_ACCOUNT_EXCEPTION_TITLE)).andReturn(titleLabel);

        // expect creating redirector to the login form
        expect(RedirectToLoginFormEventListenerFactory.createDefaultRedirector()).
            andReturn(listenerMock);

        // expect showing the message box
        expect(Messagebox.show(
            messageLabel,
            titleLabel,
            Messagebox.OK,
            Messagebox.EXCLAMATION,
            listenerMock)).andReturn(Messagebox.OK);

        replayAll();

        controller.onUnknownAccountException();

        verifyAll();
    }*/

    /**
     * This test checks the {@link ChangePasswordFormControllerImpl#onIncorrectCredentialsException()}
     * functionality.
     * <p>
     * It shows the message box which informs the user that the account does not exist
     * and it leaves the user on the same page.
     */
    @Test
    public void onIncorrectCredentialsExceptionTest() throws Exception {
     // variables
        String messageLabel = getRandomWord();
        String titleLabel = getRandomWord();

        // expect resolving labels
        expect(Labels.getLabel(SecurityLabels.ON_INCORRECT_CREDENTIALS_EXCEPTION_MESSAGE)).andReturn(messageLabel);
        expect(Labels.getLabel(SecurityLabels.ON_INCORRECT_CREDENTIALS_EXCEPTION_TITLE)).andReturn(titleLabel);

        // expect showing the message box
        expect(Messagebox.show(
            messageLabel,
            titleLabel,
            Messagebox.OK,
            Messagebox.EXCLAMATION)).andReturn(Messagebox.OK);

        replayAll();

        controller.onIncorrectCredentialsException();

        verifyAll();
    }

    /**
     * This test checks the {@link ChangePasswordFormControllerImpl#onAccountNotExpiredException()}
     * functionality. It shows the message box which informs the user and then it redirects the
     * user to the login form.
     */
    /*
    @Test
    public void onAccountNotExpiredExceptionTest() throws Exception {
        // variables
        String messageLabel = getRandomWord();
        String titleLabel = getRandomWord();

        // mocks
        RedirectToURIEventListener listenerMock =
                createMock(RedirectToURIEventListener.class);

        // expect resolving labels
        expect(Labels.getLabel(SecurityLabels.ON_ACCOUNT_NOT_EXPIRED_EXCEPTION_MESSAGE)).andReturn(messageLabel);
        expect(Labels.getLabel(SecurityLabels.ON_ACCOUNT_NOT_EXPIRED_EXCEPTION_TITLE)).andReturn(titleLabel);

        // expect creating redirector to the login form
        expect(RedirectToLoginFormEventListenerFactory.createDefaultRedirector()).
            andReturn(listenerMock);

        // expect showing the message box
        expect(Messagebox.show(
            messageLabel,
            titleLabel,
            Messagebox.OK,
            Messagebox.EXCLAMATION,
            listenerMock)).andReturn(Messagebox.OK);

        replayAll();

        controller.onAccountNotExpiredException();

        verifyAll();
    }*/
}
