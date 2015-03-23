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
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import org.apache.shiro.subject.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.ServiceRegistration;
import org.panifex.itest.web.base.support.PageletTestHelper;
import org.panifex.module.api.security.SecurityUtilService;

/**
 * Integration tests for the login pagelet that use mocked security util
 * service instead dedicated security modules.
 */
public abstract class LoginPageletSecUtilTest extends LoginPageletTest {

    // mocks
    protected final SecurityUtilService securityUtilServiceMock =
            createMock(SecurityUtilService.class);

    // service registration
    private ServiceRegistration<SecurityUtilService> securityUtilServiceRegistration;

    public LoginPageletSecUtilTest(PageletTestHelper testHelper) {
        super(testHelper);
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
    @Override
    public void testLogin() throws Exception {
        // mocks
        Subject subjectMock = createMock(Subject.class);

        // expect getting subject
        expect(securityUtilServiceMock.getSubject()).andReturn(subjectMock);

        // run tests
        Object[] mocks = new Object[] { subjectMock, securityUtilServiceMock };
        replay(mocks);

        super.testLogin();

        verify(mocks);
    }

}
