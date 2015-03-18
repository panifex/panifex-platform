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
package org.panifex.itest.web.vaadin.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.ServiceRegistration;
import org.panifex.module.api.security.SecurityUtilService;
import org.panifex.test.support.IWebTestSupport;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@RunWith(PaxExam.class)
public class LoginVaadinPageletTest extends IWebTestSupport {

    // mocks
    private final SecurityUtilService securityUtilServiceMock =
            createMock(SecurityUtilService.class);

    // service registration
    private ServiceRegistration<SecurityUtilService> securityUtilServiceRegistration;

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("com.vaadin", "vaadin-server").versionAsInProject(),
                mavenBundle("com.vaadin", "vaadin-shared").versionAsInProject(),
                mavenBundle("com.vaadin.external.flute", "flute").versionAsInProject(),
                mavenBundle("com.vaadin.external.google", "android-json").versionAsInProject(),
                mavenBundle("com.vaadin.external.google", "guava").versionAsInProject(),
                mavenBundle("com.vaadin.external.streamhtmlparser", "streamhtmlparser-jsilver").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-vaadin-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-spi").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-vaadin-layout").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-vaadin-runtime").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-vaadin-security").versionAsInProject());
    }

    @Before
    public void setUp() throws Exception {
        // register service
        securityUtilServiceRegistration =
                registerService(SecurityUtilService.class, securityUtilServiceMock);
    }

    @After
    public void cleanup() throws Exception {
        reset(securityUtilServiceMock);

        securityUtilServiceRegistration.unregister();
    }

    @Test
    public void testResetButton() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL + "/login");

        webClient.waitForBackgroundJavaScript(20_000L);

        // find username and password text input elements
        HtmlTextInput usernameTextInput = page.getHtmlElementById("username-txt");
        HtmlTextInput passwordTextInput = page.getHtmlElementById("password-txt");

        // assert username and password input fields are empty
        assertEquals(StringUtils.EMPTY, usernameTextInput.getValueAttribute());
        assertEquals(StringUtils.EMPTY, passwordTextInput.getValueAttribute());

        // type username and password
        usernameTextInput.setValueAttribute("user");
        passwordTextInput.setValueAttribute("pass");

        // reset form
        HtmlDivision resetButton = page.getHtmlElementById("reset-btn");
        resetButton.dblClick();

        webClient.waitForBackgroundJavaScript(1_000L);

        // assert username and password input fields are empty
        assertEquals(StringUtils.EMPTY, usernameTextInput.getValueAttribute());
        assertEquals(StringUtils.EMPTY, passwordTextInput.getValueAttribute());

        webClient.closeAllWindows();
    }
}
