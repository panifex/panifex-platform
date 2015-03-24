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
package org.panifex.itest.web.base.security;

import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.panifex.itest.web.base.support.PageletTestHelper;
import org.panifex.itest.web.base.support.PageletTestSupport;
import org.panifex.itest.web.base.support.html.ButtonElement;
import org.panifex.itest.web.base.support.html.TextInputElement;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public abstract class LoginPageletTest extends PageletTestSupport {

    public LoginPageletTest(PageletTestHelper testHelper) {
        super(testHelper);
    }

    @Override
    protected Option[] webConfigure() {
        return OptionUtils.combine(
                super.webConfigure(),

                mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-spi").versionAsInProject());
    }

    @Test
    public void testResetButton() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL + "/login");

        webClient.waitForBackgroundJavaScript(20_000L);

        // find username and password text input elements
        TextInputElement usernameTextInput = getTextInputElementById(page, "username-txt");
        TextInputElement passwordTextInput = getTextInputElementById(page, "password-txt");

        // assert username and password input fields are empty
        assertEquals(StringUtils.EMPTY, usernameTextInput.getValueAttribute());
        assertEquals(StringUtils.EMPTY, passwordTextInput.getValueAttribute());

        // type username and password
        usernameTextInput.setValueAttribute("user");
        passwordTextInput.setValueAttribute("pass");

        Thread.sleep(1_000L);

        // reset form
        ButtonElement resetButton = getButtonElementById(page, "reset-btn");
        resetButton.click();

        webClient.waitForBackgroundJavaScript(1_000L);
        Thread.sleep(1_000L);

        // assert username and password input fields are empty
        assertEquals(StringUtils.EMPTY, usernameTextInput.getValueAttribute());
        assertEquals(StringUtils.EMPTY, passwordTextInput.getValueAttribute());

        webClient.closeAllWindows();
    }

    @Test
    public void testLogin() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL + "/login");

        webClient.waitForBackgroundJavaScript(20_000L);

        // find username and password text input elements
        TextInputElement usernameInputElement = getTextInputElementById(page, "username-txt");
        TextInputElement passwordInputElement = getTextInputElementById(page, "password-txt");

        // type username and password
        usernameInputElement.setValueAttribute("user");
        passwordInputElement.setValueAttribute("pass");

        Thread.sleep(1_000L);

        // click on login
        ButtonElement loginButtonElement = getButtonElementById(page, "login-btn");
        loginButtonElement.click();

        Thread.sleep(1_000L);

        webClient.closeAllWindows();
    }
}
