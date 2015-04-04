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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.osgi.framework.ServiceRegistration;
import org.panifex.itest.web.base.support.PageletTestHelper;
import org.panifex.module.api.security.AuthenticationService;

/**
 * Integration tests for the login pagelet that use panifex-security-shiro module
 * and a mocked authentication service.
 */
public abstract class LoginPageletShiroTest extends LoginPageletTest {

    // mocks
    private final AuthenticationService authServiceMock =
            EasyMock.createMock(AuthenticationService.class);

    private ServiceRegistration<AuthenticationService> authServiceRegistration;

    public LoginPageletShiroTest(PageletTestHelper testHelper) {
        super(testHelper);
    }

    @Override
    protected Option[] webConfigure() {
        return OptionUtils.combine(
                super.webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").versionAsInProject(),
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm").versionAsInProject(),
                mavenBundle("org.apache.felix", "org.apache.felix.configadmin").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-core").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-ehcache").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-web").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-security-shiro").versionAsInProject());
    }

    @Before
    public void setUp() throws Exception {
        // expect getting auth service name
        expect(authServiceMock.getName()).andReturn("authService").anyTimes();

        // register auth service
        replay(authServiceMock);
        authServiceRegistration = registerService(AuthenticationService.class, authServiceMock);

        Thread.sleep(5_000L);

        verify(authServiceMock);
        reset(authServiceMock);
    }

    @After
    public void cleanup() throws Exception {
        reset(authServiceMock);

        // expect getting auth service name
        expect(authServiceMock.getName()).andReturn("authService").anyTimes();

        replay(authServiceMock);

        authServiceRegistration.unregister();

        Thread.sleep(5_000L);

        verify(authServiceMock);
        reset(authServiceMock);
    }

    @Override
    @Test
    public void testLogin() throws Exception {
        // mocks
        AuthenticationInfo authInfoMock = createMock(AuthenticationInfo.class);
        PrincipalCollection principalsMock = createMock(PrincipalCollection.class);

        // expect successfully authentication
        expect(authServiceMock.supports(isA(UsernamePasswordToken.class))).andReturn(Boolean.TRUE);
        expect(authServiceMock.getAuthenticationInfo(isA(UsernamePasswordToken.class))).andReturn(authInfoMock);

        // expect getting principals
        expect(authInfoMock.getPrincipals()).andReturn(principalsMock).anyTimes();
        expect(principalsMock.isEmpty()).andReturn(true).anyTimes();

        Object[] mocks = new Object[] {authInfoMock, principalsMock, authServiceMock};

        replay(mocks);

        super.testLogin();

        verify(mocks);
    }
}
