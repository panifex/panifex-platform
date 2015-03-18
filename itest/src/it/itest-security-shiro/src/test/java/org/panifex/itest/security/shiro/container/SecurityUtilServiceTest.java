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
package org.panifex.itest.security.shiro.container;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.inject.Inject;

import org.apache.shiro.subject.Subject;
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
import org.panifex.module.api.security.SecurityUtilService;
import org.panifex.test.support.IWebTestSupport;

/**
 * Integration tests for {@link org.panifex.security.shiro.service.SecurityUtilServiceImpl
 * SecurityUtilServiceImpl} class.
 */
@RunWith(PaxExam.class)
public class SecurityUtilServiceTest extends IWebTestSupport {

    @Inject
    private SecurityUtilService securityUtilService;

    // mocks
    private final AuthenticationService authServiceMock =
            createMock(AuthenticationService.class);

    // service registrations
    private ServiceRegistration<AuthenticationService> authServiceRegistration;

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
            webConfigure(),

            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm").versionAsInProject(),
            mavenBundle("org.apache.felix", "org.apache.felix.configadmin").versionAsInProject(),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").versionAsInProject(),
            mavenBundle("org.apache.shiro", "shiro-core").versionAsInProject(),
            mavenBundle("org.apache.shiro", "shiro-ehcache").versionAsInProject(),
            mavenBundle("org.apache.shiro", "shiro-web").versionAsInProject(),
            mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
            mavenBundle("org.panifex", "panifex-security-shiro").versionAsInProject(),
            mavenBundle("org.panifex", "panifex-test-support").versionAsInProject());
    }

    @Before
    public void setUp() throws Exception {
        // expect getting auth service name
        expect(authServiceMock.getName()).andReturn("authService").atLeastOnce();

        // register auth service
        replay(authServiceMock);
        authServiceRegistration = registerService(AuthenticationService.class, authServiceMock);

        Thread.sleep(1_000L);
    }

    @After
    public void cleanup() {
        reset(authServiceMock);

        authServiceRegistration.unregister();
    }

    @Test
    public void testSecurityUtilServiceBinded() {
        assertNotNull(securityUtilService);

        assertEquals("org.panifex.security.shiro.service.SecurityUtilServiceImpl",
                securityUtilService.getClass().getName());

        Subject subject = securityUtilService.getSubject();
        assertNotNull(subject);
    }
}
