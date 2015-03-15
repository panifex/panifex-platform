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
package org.panifex.itest.web.zk.security;

import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import net.sourceforge.htmlunit.corejs.javascript.NativeObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.panifex.test.support.IWebTestSupport;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

@RunWith(PaxExam.class)
public class LoginZkPageletTest extends IWebTestSupport {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-zk-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-spi").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-layout").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-runtime").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-security").versionAsInProject());
    }

    @Test
    public void httpGetFromServletTest() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL + "/zk/login");

        // find username and password text input elements
        HTMLInputElement usernameInputElement = (HTMLInputElement) (
                (NativeObject) page.executeJavaScript("jq('$username-txt')")
                .getJavaScriptResult()).get(0);
        HTMLInputElement passwordInputElement = (HTMLInputElement) (
                (NativeObject) page.executeJavaScript("jq('$password-txt')")
                .getJavaScriptResult()).get(0);

        // type username and password
        usernameInputElement.setValue("user");
        passwordInputElement.setValue("pass");

        // click on login
        HTMLButtonElement loginButtonElement = (HTMLButtonElement) (
                (NativeObject) page.executeJavaScript("jq('$login-btn')")
                .getJavaScriptResult()).get(0);
        loginButtonElement.click();

        webClient.closeAllWindows();
    }

    @Test
    public void testResetButton() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL + "/zk/login");

        // find username and password text input elements
        HTMLInputElement usernameInputElement = (HTMLInputElement) (
                (NativeObject) page.executeJavaScript("jq('$username-txt')")
                .getJavaScriptResult()).get(0);
        HTMLInputElement passwordInputElement = (HTMLInputElement) (
                (NativeObject) page.executeJavaScript("jq('$password-txt')")
                .getJavaScriptResult()).get(0);

        // type username and password
        usernameInputElement.focus();
        usernameInputElement.setValue("user");
        passwordInputElement.focus();
        passwordInputElement.setValue("pass");

        // click on reset button
        HTMLButtonElement resetButtonElement = (HTMLButtonElement) (
                (NativeObject) page.executeJavaScript("jq('$reset-btn')")
                .getJavaScriptResult()).get(0);
        resetButtonElement.click();

        Thread.sleep(1_000L);

        // assert username and password input elements are empty

        assertTrue(usernameInputElement.getValue().isEmpty());
        assertTrue(passwordInputElement.getValue().isEmpty());

        webClient.closeAllWindows();
    }
}
