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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import net.sourceforge.htmlunit.corejs.javascript.NativeObject;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.ServiceRegistration;
import org.panifex.module.api.security.AuthenticationService;
import org.panifex.test.support.IWebTestSupport;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

@RunWith(PaxExam.class)
public class LoginZkPageletShiroTest extends IWebTestSupport {

    private final AuthenticationService authServiceMock =
            EasyMock.createMock(AuthenticationService.class);

    private ServiceRegistration<AuthenticationService> authServiceRegistration;

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").versionAsInProject(),
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm").versionAsInProject(),
                mavenBundle("org.apache.felix", "org.apache.felix.configadmin").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-core").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-ehcache").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-web").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-zk-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-security-shiro").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-spi").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-layout").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-runtime").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-security").versionAsInProject());
    }

    @Before
    public void setUp() throws Exception {
        // expect getting auth service name
        expect(authServiceMock.getName()).andReturn("authService").atLeastOnce();

        // register auth service
        replay(authServiceMock);
        authServiceRegistration = registerService(AuthenticationService.class, authServiceMock);

        Thread.sleep(1_000L);

        reset(authServiceMock);
    }

    @After
    public void cleanup() {
        authServiceRegistration.unregister();
    }

    @Test
    public void testLogin() throws Exception {
        // variables
        String username = "user";
        String password = "pass";

        // mocks
        AuthenticationInfo authInfo = createMock(AuthenticationInfo.class);

        // expect successfully authentication
        expect(authServiceMock.supports(isA(UsernamePasswordToken.class))).andReturn(Boolean.TRUE);
        expect(authServiceMock.getAuthenticationInfo(isA(UsernamePasswordToken.class))).andReturn(authInfo);

        replay(authServiceMock);

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
        usernameInputElement.setValue(username);
        passwordInputElement.setValue(password);

        Thread.sleep(1_000L);

        // click on login
        HTMLButtonElement loginButtonElement = (HTMLButtonElement) (
                (NativeObject) page.executeJavaScript("jq('$login-btn')")
                .getJavaScriptResult()).get(0);
        loginButtonElement.click();

        Thread.sleep(1_000L);

        webClient.closeAllWindows();

        verify(authServiceMock);
    }
}
