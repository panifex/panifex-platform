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
package org.panifex.web.spi.layout;

import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.panifex.module.api.security.SecurityUtilService;
import org.panifex.module.api.security.SecurityUtilServiceTracker;
import org.panifex.test.support.TestSupport;

/**
 * Unit tests for {@link LayoutViewModelImpl} class.
 */
public class LayoutViewModelImplTest extends TestSupport {

    // services
    private final SecurityUtilService securityUtilServiceMock =
            createMock(SecurityUtilService.class);

    // trackers
    private final SecurityUtilServiceTracker securityUtilServiceTrackerMock =
            createMock(SecurityUtilServiceTracker.class);

    private final LayoutViewModelImpl viewModel =
            new LayoutViewModelImpl(securityUtilServiceTrackerMock);

    @Before
    public void setUp() {
        resetAll();

        // expect returning services from trackers
        expect(securityUtilServiceTrackerMock.service()).andReturn(securityUtilServiceMock);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructWithNullTracker() {
        new LayoutViewModelImpl(null);
    }

    /**
     * Tests the {@link LayoutViewModelImpl#getIsUserAuthenticated()} method in case the current
     * subject is authenticated.
     */
    @Test
    public void testIsUserAuthenticated() {
        // mocks
        Subject currentSubject = createMock(Subject.class);

        // expect getting current subject
        expect(securityUtilServiceMock.getSubject()).andReturn(currentSubject);

        // subject is authenticated
        expect(currentSubject.isAuthenticated()).andReturn(true);

        // perform test
        replayAll();
        boolean isAuthenticated = viewModel.getIsUserAuthenticated();
        verifyAll();

        // it should be authenticated
        assertTrue(isAuthenticated);
    }

    /**
     * Tests the {@link LayoutViewModelImpl#getIsUserLogged()} method in case the current
     * subject is remembered.
     * <p>
     * The method should return true because the subject is logged when it is remembered or
     * it is authenticated.
     */
    @Test
    public void testIsUserLoggedWhenSubjectIsRemebered() {
        // mocks
        Subject currentSubject = createMock(Subject.class);

        // expect getting current subject
        expect(securityUtilServiceMock.getSubject()).andReturn(currentSubject);

        // subject is remembered
        expect(currentSubject.isRemembered()).andReturn(true);

        // perform test
        replayAll();
        boolean isLogged = viewModel.getIsUserLogged();
        verifyAll();

        // it should be logged
        assertTrue(isLogged);
    }

    /**
     * Tests the {@link LayoutViewModelImpl#getIsUserLogged()} method in case the current
     * subject is not remembered, but it is authenticated.
     * <p>
     * The method should return true because the subject is logged when it is remembered or
     * it is authenticated.
     */
    @Test
    public void testIsUserLoggedWhenSubjectIsAuthenticated() {
        // mocks
        Subject currentSubject = createMock(Subject.class);

        // expect getting current subject
        expect(securityUtilServiceMock.getSubject()).andReturn(currentSubject);

        // subject is not remembered
        expect(currentSubject.isRemembered()).andReturn(false);

        // subject is authenticated
        expect(currentSubject.isAuthenticated()).andReturn(true);

        // perform test
        replayAll();
        boolean isLogged = viewModel.getIsUserLogged();
        verifyAll();

        // it should be logged
        assertTrue(isLogged);
    }

    /**
     * Tests the {@link LayoutViewModelImpl#getIsUserLogged()} method in case the current
     * subject is not remembered, nor authenticated.
     * <p>
     * The method should return false because the subject is logged when it is remembered or
     * it is authenticated.
     */
    @Test
    public void testIsUserLoggedWhenSubjectIsNotAuthenticated() {
        // mocks
        Subject currentSubject = createMock(Subject.class);

        // expect getting current subject
        expect(securityUtilServiceMock.getSubject()).andReturn(currentSubject);

        // subject is not remembered nor authenticated
        expect(currentSubject.isRemembered()).andReturn(false);
        expect(currentSubject.isAuthenticated()).andReturn(false);

        // perform test
        replayAll();
        boolean isLogged = viewModel.getIsUserLogged();
        verifyAll();

        // it should be logged
        assertFalse(isLogged);
    }
}
