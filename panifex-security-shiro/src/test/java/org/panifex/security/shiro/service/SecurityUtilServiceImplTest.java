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
package org.panifex.security.shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.security.SecurityUtilService;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for {@link SecurityUtilServiceImpl} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
public class SecurityUtilServiceImplTest extends TestSupport {

    private final SecurityUtilService service = new SecurityUtilServiceImpl();

    @BeforeClass
    public static void setUp() {
        mockStatic(SecurityUtils.class);
    }

    @Before
    public void prepare() {
        resetAll();
    }

    @Test
    public void testGetSubject() {
        // mocks
        Subject subject = createMock(Subject.class);

        // expect getting subject
        expect(SecurityUtils.getSubject()).andReturn(subject);

        // perform test
        replayAll();
        Subject actualSubject = service.getSubject();
        verifyAll();

        assertTrue(actualSubject == subject);
    }
}
